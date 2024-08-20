package com.socialnetwork.profile.config;

import com.google.gson.Gson;
import com.socialnetwork.profile.dto.response.ResponseObject;
import com.socialnetwork.profile.exception.ErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        var errorCode = ErrorCode.UNAUTHENTICATED;

        response.setStatus(errorCode.getHttpStatusCode().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        var gson = new Gson();
        response.getWriter().write(gson.toJson(ResponseObject.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage()).build()));
    }
}
