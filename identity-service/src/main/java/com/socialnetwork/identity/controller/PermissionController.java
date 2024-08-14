package com.socialnetwork.identity.controller;

import com.socialnetwork.identity.dto.request.PermissionCreateRequestDTO;
import com.socialnetwork.identity.dto.request.PermissionUpdateRequestDTO;
import com.socialnetwork.identity.dto.response.CommonPermissionResponseDTO;
import com.socialnetwork.identity.dto.response.ResponseObject;
import com.socialnetwork.identity.service.PermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("/v1/permission")
public class PermissionController {
    @Autowired
    PermissionService permissionService;

    @PostMapping
    public ResponseObject<CommonPermissionResponseDTO> create(@RequestBody PermissionCreateRequestDTO requestDTO) {
        return ResponseObject.<CommonPermissionResponseDTO>builder()
                .data(permissionService.create(requestDTO))
                .build();
    }

    @GetMapping("/all")
    public ResponseObject<CommonPermissionResponseDTO[]> getAll() {
        return ResponseObject.<CommonPermissionResponseDTO[]>builder()
                .data(permissionService.getAll())
                .build();
    }

    @PutMapping
    public ResponseObject<CommonPermissionResponseDTO> update(@RequestBody PermissionUpdateRequestDTO requestDTO) {
        return ResponseObject.<CommonPermissionResponseDTO>builder()
                .data(permissionService.update(requestDTO))
                .build();
    }

    @DeleteMapping("/{permissionName}")
    public ResponseObject<Void> delete(@PathVariable String permissionName) {
        permissionService.delete(permissionName);
        return ResponseObject.<Void>builder().build();
    }
}
