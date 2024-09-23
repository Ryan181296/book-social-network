package com.socialnetwork.identity.config;

import com.socialnetwork.identity.enums.RoleType;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SecurityConfig {
    @Autowired
    CustomJwtDecoder customJwtDecoder;

    @Autowired
    JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    CustomAccessDeniedHandler customAccessDeniedHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(request ->
                request.requestMatchers("/v1/auth/login").permitAll()
                        .requestMatchers("/v1/auth/google").permitAll()
                        .requestMatchers("/v1/test/import-users").permitAll()
                        .requestMatchers("/v1/auth/refresh-token").permitAll()
                        .requestMatchers("/v1/auth/verify-token").permitAll()
                        .requestMatchers("/v1/notification").permitAll()
                        .requestMatchers("/v1/permission").permitAll()
                        .requestMatchers("/v1/permission/*").permitAll()
                        .requestMatchers("/v1/role").permitAll()
                        .requestMatchers("/v1/role/*").permitAll()
                        .requestMatchers("/v1/user/registration").permitAll()
                        .requestMatchers("/v1/**").authenticated()
                        .requestMatchers("/v1/user/all").hasRole(RoleType.ADMIN.name())
                        .requestMatchers("/v1/permission/all").hasRole(RoleType.ADMIN.name())
                        .requestMatchers("/v1/role/all").hasRole(RoleType.ADMIN.name()).anyRequest().authenticated());

        httpSecurity.oauth2ResourceServer(oauth2 ->
                oauth2.jwt(jwtConfigurer -> jwtConfigurer.decoder(customJwtDecoder)
                                .jwtAuthenticationConverter(jwtAuthenticationConverter()))
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                        .accessDeniedHandler(customAccessDeniedHandler)
        );

        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        return httpSecurity.build();
    }

    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter() {
        var jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("");

        var jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }
}
