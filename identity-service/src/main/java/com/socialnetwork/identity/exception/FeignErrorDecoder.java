package com.socialnetwork.identity.exception;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
public class FeignErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        var responseBody = response.body();
        var responseData = Objects.nonNull(responseBody) ? responseBody.toString() : "null";

        log.error("methodKey = {}, statusCode = {}, message = {}, data = {}", methodKey, response.status(), response.reason(), responseData);
        return new Exception(response.reason());
    }
}
