package com.socialnetwork.identity.service;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.socialnetwork.identity.dto.response.DiscountResponseDTO;
import com.socialnetwork.identity.dto.response.PageResponse;
import com.socialnetwork.identity.entity.DiscountEntity;
import com.socialnetwork.identity.exception.CustomException;
import com.socialnetwork.identity.exception.ErrorCode;
import com.socialnetwork.identity.mapper.JsonMapper;
import com.socialnetwork.identity.repository.DiscountRepository;
import com.socialnetwork.identity.cache.RedisKeyManager;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DiscountService {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static final int BATCH_SIZE = 100;

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    RedissonClient redissonClient;

    @Autowired
    DiscountRepository discountRepository;

    @Transactional
    public void importDiscounts(String filePath, String createDate) {
        var objectMapper = new ObjectMapper();
        var jsonFactory = new JsonFactory();
        try (var fileInputStream = new FileInputStream(filePath);
             var jsonParser = jsonFactory.createParser(fileInputStream)) {

            jsonParser.setCodec(objectMapper);
            jsonParser.nextToken();

            List<DiscountEntity> records = new LinkedList<>();
            while (jsonParser.nextToken() == JsonToken.START_OBJECT) {
                var record = jsonParser.readValueAs(DiscountEntity.class);
                record.setCreateDate(dateFormat.parse(createDate));
                records.add(record);

                if (records.size() >= BATCH_SIZE) {
                    saveToDatabase(records);
                    records.clear(); // Reset batch
                }
            }

            // Save any remaining records
            if (!records.isEmpty()) {
                saveToDatabase(records);
            }
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new CustomException(ErrorCode.COMMON_MESSAGE);
        }
    }

    private void saveToDatabase(List<DiscountEntity> records) {
        discountRepository.saveAll(records);
        entityManager.flush();
        entityManager.clear();
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void applyDiscount(String discountId) {
        var lockKey = RedisKeyManager.getDiscountKey(discountId);
        var lock = redissonClient.getLock(lockKey);
        try {
            if (lock.tryLock(100, 60, TimeUnit.SECONDS)) {
                try {
                    var discountEntity = discountRepository.findById(discountId).orElseThrow(() -> new CustomException(ErrorCode.DISCOUNT_INVALID));
                    if (discountEntity.getIsUsed()) {
                        throw new CustomException(ErrorCode.DISCOUNT_USED);
                    }
                    discountEntity.setIsUsed(true);
                    discountRepository.save(discountEntity);
                } finally {
                    if (lock.isHeldByCurrentThread()) {
                        lock.unlock();
                    }
                }
            } else {
                throw new CustomException(ErrorCode.DISCOUNT_USED);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new CustomException(ErrorCode.DISCOUNT_USED);
        }
    }

    public PageResponse<DiscountResponseDTO> getDiscounts(String name, String description, int pageNumber, int pageSize) {
        var pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        var pageData = discountRepository.findAll(name, description, pageRequest);

        List<DiscountResponseDTO> data = pageData.getContent().stream().map(this::mapToUserTestResponseDTO).toList();

        return PageResponse.<DiscountResponseDTO>builder()
                .data(data)
                .totalPages(pageData.getTotalPages())
                .totalItems(pageData.getTotalElements())
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .build();
    }

    private DiscountResponseDTO mapToUserTestResponseDTO(DiscountEntity testEntity) {
        return JsonMapper.map(testEntity, DiscountResponseDTO.class);
    }
}