package com.socialnetwork.identity.controller;

import com.socialnetwork.identity.dto.response.PageResponse;
import com.socialnetwork.identity.dto.response.ResponseObject;
import com.socialnetwork.identity.dto.response.UserTestResponseDTO;
import com.socialnetwork.identity.service.TestService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v1/test")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TestController {
    @Autowired
    TestService testService;

    @PostMapping(value = "/import-users", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseObject<Void> importFile(@RequestBody String filePath) {
        testService.importUsers(filePath);
        return ResponseObject.<Void>builder()
                .build();
    }

    @GetMapping(value = "/users", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseObject<PageResponse<UserTestResponseDTO>> getUsers(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                                                                      @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                                                                      @RequestParam(value = "address", required = false) String address,
                                                                      @RequestParam(value = "description", required = false) String description) {
        return ResponseObject.<PageResponse<UserTestResponseDTO>>builder()
                .result(testService.getUsers(address, description, pageNumber, pageSize))
                .build();
    }
}
