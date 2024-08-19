package com.socialnetwork.identity.exception;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FeignErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        log.error("methodKey = {}, statusCode = {}, message = {}, data", response.status(), methodKey, response.reason());
        return new Exception(response.reason());
    }
}
