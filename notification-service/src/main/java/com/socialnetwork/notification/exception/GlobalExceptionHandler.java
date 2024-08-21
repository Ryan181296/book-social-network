package com.socialnetwork.notification.exception;

import com.socialnetwork.notification.dto.response.ResponseObject;
import com.socialnetwork.notification.util.MethodArgumentNotValidExceptionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Objects;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    ResponseEntity<ResponseObject<?>> handlingRuntimeException(RuntimeException e) {
        var errorCode = ErrorCode.COMMON_MESSAGE;
        return ResponseEntity.badRequest().body(ResponseObject.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build());
    }

    @ExceptionHandler(CustomException.class)
    ResponseEntity<ResponseObject<?>> handlingServiceException(CustomException e) {
        var errorCode = e.getErrorCode();
        return ResponseEntity.badRequest().body(ResponseObject.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    ResponseEntity<ResponseObject<?>> handlingNotFoundException(NoHandlerFoundException e) {
        var errorCode = ErrorCode.NOT_FOUND;

        return ResponseEntity.status(errorCode.getHttpStatusCode()).body(ResponseObject.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ResponseObject<?>> handlingValidationException(MethodArgumentNotValidException e) {
        var fieldError = e.getFieldError();
        var errorCode = ErrorCode.UNDEFINED;
        if (Objects.nonNull(fieldError)) {
            try {
                var errorCodeStr = e.getFieldError().getDefaultMessage();
                errorCode = ErrorCode.valueOf(errorCodeStr);
            } catch (IllegalArgumentException ex) {
                log.info(ex.getMessage());
            }
        }

        return ResponseEntity.badRequest().body(ResponseObject.builder()
                .code(errorCode.getCode())
                .message(MethodArgumentNotValidExceptionUtils.getMessage(e, errorCode.getMessage()))
                .build());
    }
}
