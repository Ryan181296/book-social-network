package com.socialnetwork.identity.controller;

import com.socialnetwork.identity.dto.request.UserCreationRequestDTO;
import com.socialnetwork.identity.dto.request.UserUpdateRequestDTO;
import com.socialnetwork.identity.dto.response.CommonUserResponseDTO;
import com.socialnetwork.identity.dto.response.ResponseObject;
import com.socialnetwork.identity.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/v1/user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping
    public ResponseObject<CommonUserResponseDTO> create(@Valid @RequestBody UserCreationRequestDTO requestDTO) {
        return ResponseObject.<CommonUserResponseDTO>builder()
                .data(userService.create(requestDTO))
                .build();
    }

    @GetMapping
    public ResponseObject<CommonUserResponseDTO> getProfile() {
        return ResponseObject.<CommonUserResponseDTO>builder()
                .data(userService.getProfile())
                .build();
    }

    @PutMapping
    public ResponseObject<CommonUserResponseDTO> update(@RequestBody UserUpdateRequestDTO requestDTO) {
        return ResponseObject.<CommonUserResponseDTO>builder()
                .data(userService.update(requestDTO))
                .build();
    }

    @GetMapping("/{userId}")
    public ResponseObject<CommonUserResponseDTO> getById(@PathVariable("userId") String userId) {
        return ResponseObject.<CommonUserResponseDTO>builder()
                .data(userService.getById(userId))
                .build();
    }

    @GetMapping("/all")
    public ResponseObject<CommonUserResponseDTO[]> getAll() {
        return ResponseObject.<CommonUserResponseDTO[]>builder()
                .data(userService.getAll())
                .build();
    }

    @PutMapping("/{userId}")
    public ResponseObject<CommonUserResponseDTO> updateById(@PathVariable("userId") String userId, @RequestBody UserUpdateRequestDTO requestDTO) {
        return ResponseObject.<CommonUserResponseDTO>builder()
                .data(userService.updateById(userId, requestDTO))
                .build();
    }
}
