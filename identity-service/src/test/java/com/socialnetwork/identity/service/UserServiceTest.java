package com.socialnetwork.identity.service;

import com.socialnetwork.identity.dto.request.UserCreationRequestDTO;
import com.socialnetwork.identity.entity.UserEntity;
import com.socialnetwork.identity.repository.UserRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/test.properties")
@FieldDefaults(level = AccessLevel.PRIVATE)
class UserServiceTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserService userService;

    @MockBean
    UserRepository userRepository;

    UserEntity userEntity;
    UserCreationRequestDTO requestDTO;

    @BeforeEach
    public void initData() {
        requestDTO = UserCreationRequestDTO.builder()
                .username("johnydang")
                .password("Theluong1503")
                .dob(LocalDate.of(1990, 1, 1))
                .build();

        userEntity = UserEntity.builder()
                .id("34j4-d6dh-75gs")
                .username("johnydang")
                .build();
    }

    @Test
    void create_requestValid_success() {
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.save(any())).thenReturn(userEntity);

        var response = userService.create(requestDTO);

        Assertions.assertThat(response.getId()).isEqualTo("34j4-d6dh-75gs");
        Assertions.assertThat(response.getUsername()).isEqualTo("johnydang");
    }
}
