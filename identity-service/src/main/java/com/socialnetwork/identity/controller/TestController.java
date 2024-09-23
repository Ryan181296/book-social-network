package com.socialnetwork.identity.controller;

import com.socialnetwork.identity.dto.response.ResponseObject;
import com.socialnetwork.identity.service.TestService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
