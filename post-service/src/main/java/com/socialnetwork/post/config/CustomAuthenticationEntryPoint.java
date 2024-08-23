package com.socialnetwork.post.config;

import com.socialnetwork.post.dto.response.ResponseObject;
import com.socialnetwork.post.exception.ErrorCode;
import com.socialnetwork.post.mapper.JsonMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        var errorCode = ErrorCode.UNAUTHENTICATED;

        response.setStatus(errorCode.getCode());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        var responseJson = JsonMapper.toJson(ResponseObject.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build());

        response.getWriter().write(Objects.requireNonNullElse(responseJson, ""));
    }
}
