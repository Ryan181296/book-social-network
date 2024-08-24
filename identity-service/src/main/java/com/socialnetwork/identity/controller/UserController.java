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
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping(path = "/v1/user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping(value = "/registration", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseObject<CommonUserResponseDTO> create(@Valid @RequestBody UserCreationRequestDTO requestDTO) {
        return ResponseObject.<CommonUserResponseDTO>builder()
                .result(userService.create(requestDTO))
                .build();
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseObject<CommonUserResponseDTO> getProfile() {
        return ResponseObject.<CommonUserResponseDTO>builder()
                .result(userService.getProfile())
                .build();
    }

    @PutMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseObject<CommonUserResponseDTO> update(@RequestBody UserUpdateRequestDTO requestDTO) {
        return ResponseObject.<CommonUserResponseDTO>builder()
                .result(userService.update(requestDTO))
                .build();
    }

    @GetMapping(value = "/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseObject<CommonUserResponseDTO> getById(@PathVariable("userId") String userId) {
        return ResponseObject.<CommonUserResponseDTO>builder()
                .result(userService.getById(userId))
                .build();
    }

    @GetMapping(value = "/all", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseObject<CommonUserResponseDTO[]> getAll() {
        return ResponseObject.<CommonUserResponseDTO[]>builder()
                .result(userService.getAll())
                .build();
    }

    @PutMapping(value = "/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseObject<CommonUserResponseDTO> updateById(@PathVariable("userId") String userId, @RequestBody UserUpdateRequestDTO requestDTO) {
        return ResponseObject.<CommonUserResponseDTO>builder()
                .result(userService.updateById(userId, requestDTO))
                .build();
    }
}
