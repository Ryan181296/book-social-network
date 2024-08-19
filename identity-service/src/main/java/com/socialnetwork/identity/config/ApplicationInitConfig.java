package com.socialnetwork.identity.config;

import com.socialnetwork.identity.entity.UserEntity;
import com.socialnetwork.identity.repository.UserRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class ApplicationInitConfig {
    @Bean
    @ConditionalOnProperty(
            prefix = "spring",
            value = "datasource.driverClassName",
            havingValue = "com.mysql.cj.jdbc.Driver")
    ApplicationRunner applicationRunner(UserRepository userRepository) {
        return arg -> {
            final var username = "admin";
            final var password = "Theluong1503";

            if (!userRepository.existsByUsername(username)) {
                var passwordEncoder = new BCryptPasswordEncoder(10);
                var userEntity = UserEntity.builder()
                        .username(username)
                        .password(passwordEncoder.encode(password))
                        .build();
                userRepository.save(userEntity);
            }
        };
    }
}
