package com.socialnetwork.identity.service;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.socialnetwork.identity.entity.TestEntity;
import com.socialnetwork.identity.exception.CustomException;
import com.socialnetwork.identity.exception.ErrorCode;
import com.socialnetwork.identity.repository.TestRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
@Slf4j
public class TestService {
    static final int BATCH_SIZE = 100;

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    TestRepository testRepository;

    @Transactional
    public void importUsers(String filePath) {
        var objectMapper = new ObjectMapper();
        var jsonFactory = new JsonFactory();
        try (var fileInputStream = new FileInputStream(filePath);
             var jsonParser = jsonFactory.createParser(fileInputStream)) {

            jsonParser.setCodec(objectMapper);
            jsonParser.nextToken();

            List<TestEntity> records = new LinkedList<>();
            while (jsonParser.nextToken() == JsonToken.START_OBJECT) {
                var record = jsonParser.readValueAs(TestEntity.class);
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
        } catch (IOException e) {
            log.info(e.getMessage());
            throw new CustomException(ErrorCode.COMMON_MESSAGE);
        }
    }

    private void saveToDatabase(List<TestEntity> records) {
        testRepository.saveAll(records);
        entityManager.flush();
        entityManager.clear();
    }
}