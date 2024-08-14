package com.socialnetwork.identity.service;

import com.socialnetwork.identity.dto.request.PermissionCreateRequestDTO;
import com.socialnetwork.identity.dto.request.PermissionUpdateRequestDTO;
import com.socialnetwork.identity.dto.response.CommonPermissionResponseDTO;
import com.socialnetwork.identity.entity.PermissionEntity;
import com.socialnetwork.identity.exception.ErrorCode;
import com.socialnetwork.identity.exception.ServiceException;
import com.socialnetwork.identity.mapper.JsonMapper;
import com.socialnetwork.identity.repository.PermissionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionService {
    @Autowired
    PermissionRepository permissionRepository;

    public CommonPermissionResponseDTO create(PermissionCreateRequestDTO requestDTO) {
        var permissionEntity = JsonMapper.map(requestDTO, PermissionEntity.class);
        if (Objects.isNull(permissionEntity)) {
            throw new ServiceException(ErrorCode.CANNOT_PARSE_DATA);
        }

        try {
            var permissionCreatedEntity = permissionRepository.save(permissionEntity);
            return JsonMapper.map(permissionCreatedEntity, CommonPermissionResponseDTO.class);
        } catch (DataIntegrityViolationException e) {
            log.error(e.getMessage());
            throw new ServiceException(ErrorCode.PERMISSION_EXISTED);
        }
    }

    public CommonPermissionResponseDTO update(PermissionUpdateRequestDTO requestDTO) {
        var permissionUpdateEntity = permissionRepository.findByName(requestDTO.getName()).orElseThrow(() -> new ServiceException(ErrorCode.PERMISSION_NOT_FOUND));

        permissionUpdateEntity.setName(requestDTO.getName());
        permissionUpdateEntity.setDescription(requestDTO.getDescription());

        var permissionUpdatedEntity = permissionRepository.save(permissionUpdateEntity);
        return JsonMapper.map(permissionUpdatedEntity, CommonPermissionResponseDTO.class);
    }

    public void delete(String permissionName) {
        permissionRepository.deleteById(permissionName);
    }

    public CommonPermissionResponseDTO[] getAll() {
        var allPermissionEntities = permissionRepository.findAll();
        return JsonMapper.map(allPermissionEntities, CommonPermissionResponseDTO[].class);
    }
}
