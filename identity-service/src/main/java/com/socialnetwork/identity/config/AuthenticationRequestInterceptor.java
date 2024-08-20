package com.socialnetwork.identity.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

@Slf4j
public class AuthenticationRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        var servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        var authHeader = Objects.nonNull(servletRequestAttributes) ? servletRequestAttributes.getRequest().getHeader(HttpHeaders.AUTHORIZATION) : null;

        log.info("Header {}", authHeader);
        if (StringUtils.hasText(authHeader)) {
            requestTemplate.header(HttpHeaders.AUTHORIZATION, authHeader);
        }
    }
}
