package com.socialnetwork.identity.controller;

import com.socialnetwork.identity.dto.request.PermissionCreateRequestDTO;
import com.socialnetwork.identity.dto.request.PermissionUpdateRequestDTO;
import com.socialnetwork.identity.dto.response.CommonPermissionResponseDTO;
import com.socialnetwork.identity.dto.response.ResponseObject;
import com.socialnetwork.identity.service.PermissionService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping(path = "/v1/permission")
public class PermissionController {
    @Autowired
    PermissionService permissionService;

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseObject<CommonPermissionResponseDTO> create(@Valid @RequestBody PermissionCreateRequestDTO requestDTO) {
        return ResponseObject.<CommonPermissionResponseDTO>builder()
                .result(permissionService.create(requestDTO))
                .build();
    }

    @GetMapping(value = "/all", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseObject<CommonPermissionResponseDTO[]> getAll() {
        return ResponseObject.<CommonPermissionResponseDTO[]>builder()
                .result(permissionService.getAll())
                .build();
    }

    @PutMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseObject<CommonPermissionResponseDTO> update(@Valid @RequestBody PermissionUpdateRequestDTO requestDTO) {
        return ResponseObject.<CommonPermissionResponseDTO>builder()
                .result(permissionService.update(requestDTO))
                .build();
    }

    @DeleteMapping(value = "/{permissionName}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseObject<Void> delete(@PathVariable String permissionName) {
        permissionService.delete(permissionName);
        return ResponseObject.<Void>builder().build();
    }
}
