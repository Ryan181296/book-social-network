package com.socialnetwork.identity.controller;

import com.socialnetwork.identity.dto.request.RoleCreateRequestDTO;
import com.socialnetwork.identity.dto.request.RoleUpdateRequestDTO;
import com.socialnetwork.identity.dto.response.CommonRoleResponseDTO;
import com.socialnetwork.identity.dto.response.ResponseObject;
import com.socialnetwork.identity.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping(path = "/v1/role")
public class RoleController {
    @Autowired
    RoleService roleService;

    @PostMapping
    @Operation(description = "Create a role")
    public ResponseObject<CommonRoleResponseDTO> create(@Valid @RequestBody RoleCreateRequestDTO requestDTO) {
        return ResponseObject.<CommonRoleResponseDTO>builder()
                .data(roleService.create(requestDTO))
                .build();
    }

    @GetMapping(value = "/all")
    @Operation(description = "Get all roles")
    public ResponseObject<CommonRoleResponseDTO[]> getAll() {
        return ResponseObject.<CommonRoleResponseDTO[]>builder()
                .data(roleService.getAll())
                .build();
    }

    @PutMapping
    @Operation(description = "Update a role")
    public ResponseObject<CommonRoleResponseDTO> update(@Valid @RequestBody RoleUpdateRequestDTO requestDTO) {
        return ResponseObject.<CommonRoleResponseDTO>builder()
                .data(roleService.update(requestDTO))
                .build();
    }

    @DeleteMapping(value = "/{roleName}")
    @Operation(description = "Delete a role")
    public ResponseObject<Void> delete(@PathVariable String roleName) {
        roleService.delete(roleName);
        return ResponseObject.<Void>builder().build();
    }
}
