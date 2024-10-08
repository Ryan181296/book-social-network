package com.socialnetwork.gateway.config;

import com.socialnetwork.gateway.dto.response.ResponseObject;
import com.socialnetwork.gateway.mapper.JsonMapper;
import com.socialnetwork.gateway.service.IdentityService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Objects;

@Component
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationFilter implements GlobalFilter, Ordered {
    @Autowired
    IdentityService identityService;

    static final String[] PUBLIC_ENDPOINTS = {
            "/user-profile/api/v1/profile/registration",
            "/identity/api/v1/discount/import",
            "/identity/api/v1/discount/list",
            "/identity/api/v1/discount/apply/.*",
            "/identity/api/v1/auth/google",
            "/identity/api/v1/auth/login",
            "/identity/api/v1/user/registration",
            "/notification/api/v1/chatbot/send-message"
    };

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if (isPublicEndpoint(exchange.getRequest())) {
            return chain.filter(exchange);
        }

        var authHeaders = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION);
        if (CollectionUtils.isEmpty(authHeaders)) {
            return unauthenticated(exchange.getResponse());
        }
        var token = authHeaders.getFirst().replace("Bearer ", "");
        return identityService.verifyToken(token).flatMap(response -> {
            if (response.getResult().getAuthenticated()) {
                return chain.filter(exchange);
            } else {
                return unauthenticated(exchange.getResponse());
            }
        }).onErrorResume(throwable -> unauthenticated(exchange.getResponse()));
    }

    @Override
    public int getOrder() {
        return -1;
    }

    Mono<Void> unauthenticated(ServerHttpResponse response) {
        var responseJson = Objects.requireNonNullElse(JsonMapper.toJson(ResponseObject.builder()
                .code(1106)
                .message("Unauthenticated")
                .build()), "");

        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        return response.writeWith(Mono.just(response.bufferFactory().wrap(responseJson.getBytes())));
    }

    private Boolean isPublicEndpoint(ServerHttpRequest request) {
        return Arrays.stream(PUBLIC_ENDPOINTS).anyMatch(endpoint -> request.getURI().getPath().matches(endpoint));
    }
}
