package com.socialnetwork.post.config;

import com.socialnetwork.post.dto.response.ResponseObject;
import com.socialnetwork.post.exception.ErrorCode;
import com.socialnetwork.post.mapper.JsonMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        var errorCode = ErrorCode.UNAUTHORIZED;

        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        var responseJson = JsonMapper.toJson(ResponseObject.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build());

        response.getWriter().write(Objects.requireNonNullElse(responseJson, ""));
    }
}
