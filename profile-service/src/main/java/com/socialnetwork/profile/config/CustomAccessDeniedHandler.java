package com.socialnetwork.profile.config;

import com.socialnetwork.profile.dto.response.ResponseObject;
import com.socialnetwork.profile.exception.ErrorCode;
import com.socialnetwork.profile.mapper.JsonMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

        response.setStatus(errorCode.getHttpStatusCode().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        var responseJson = JsonMapper.toJson(ResponseObject.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build());

        response.getWriter().write(Objects.requireNonNullElse(responseJson, ""));
    }
}
