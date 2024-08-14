package com.socialnetwork.identity.service;

import com.socialnetwork.identity.dto.request.RoleCreateRequestDTO;
import com.socialnetwork.identity.dto.request.RoleUpdateRequestDTO;
import com.socialnetwork.identity.dto.response.CommonRoleResponseDTO;
import com.socialnetwork.identity.entity.RoleEntity;
import com.socialnetwork.identity.exception.ErrorCode;
import com.socialnetwork.identity.exception.ServiceException;
import com.socialnetwork.identity.mapper.JsonMapper;
import com.socialnetwork.identity.repository.PermissionRepository;
import com.socialnetwork.identity.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PermissionRepository permissionRepository;

    public CommonRoleResponseDTO create(RoleCreateRequestDTO requestDTO) {
        var roleEntity = JsonMapper.map(requestDTO, RoleEntity.class);
        if (Objects.isNull(roleEntity)) {
            throw new ServiceException(ErrorCode.CANNOT_PARSE_DATA);
        }

        var permissionEntities = permissionRepository.findAllByName(requestDTO.getPermissions());
        roleEntity.setPermissions(new HashSet<>(permissionEntities));

        try {
            var roleCreatedEntity = roleRepository.save(roleEntity);
            return JsonMapper.map(roleCreatedEntity, CommonRoleResponseDTO.class);
        } catch (DataIntegrityViolationException e) {
            log.error(e.getMessage());
            throw new ServiceException(ErrorCode.ROLE_EXISTED);
        }
    }

    public CommonRoleResponseDTO update(RoleUpdateRequestDTO requestDTO) {
        var roleUpdateEntity = roleRepository.findByName(requestDTO.getName()).orElseThrow(() -> new ServiceException(ErrorCode.ROLE_NOT_FOUND));

        roleUpdateEntity.setName(requestDTO.getName());
        roleUpdateEntity.setDescription(requestDTO.getDescription());

        var roleUpdatedEntity = roleRepository.save(roleUpdateEntity);
        return JsonMapper.map(roleUpdatedEntity, CommonRoleResponseDTO.class);
    }

    public CommonRoleResponseDTO[] getAll() {
        var allRoleEntities = roleRepository.findAll();
        return JsonMapper.map(allRoleEntities, CommonRoleResponseDTO[].class);
    }

    public void delete(String roleName) {
        roleRepository.deleteById(roleName);
    }
}
