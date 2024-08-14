package com.socialnetwork.identity.controller;

import com.socialnetwork.identity.dto.request.UserCreationRequestDTO;
import com.socialnetwork.identity.dto.response.CommonUserResponseDTO;
import com.socialnetwork.identity.mapper.JsonMapper;
import com.socialnetwork.identity.service.UserService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.Objects;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/test.properties")
@FieldDefaults(level = AccessLevel.PRIVATE)
class UserControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    UserCreationRequestDTO requestDTO;
    CommonUserResponseDTO responseDTO;

    @BeforeEach
    public void initData() {
        requestDTO = UserCreationRequestDTO.builder()
                .username("johnydang")
                .password("Theluong1503")
                .dob(LocalDate.of(1990, 1, 1))
                .build();

        responseDTO = CommonUserResponseDTO.builder()
                .id("34j4-d6dh-75gs")
                .username("johnydang")
                .build();
    }

    @Test
    void create_requestValid_success() throws Exception {
        when(userService.create(ArgumentMatchers.any()))
                .thenReturn(responseDTO);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/user")
                        .content(Objects.requireNonNull(JsonMapper.toJson(requestDTO)))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("data.id").value("34j4-d6dh-75gs"))
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(200));
    }

    @Test
    void create_usernameInvalid_fail() throws Exception {
        requestDTO.setUsername("abc");
        when(userService.create(ArgumentMatchers.any()))
                .thenReturn(responseDTO);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/user")
                        .content(Objects.requireNonNull(JsonMapper.toJson(requestDTO)))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Username must be greater than 8 and less than 16 characters"))
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1002));
    }
}
