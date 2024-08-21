package com.socialnetwork.notification.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ErrorCode {
    NOT_FOUND(996, "Resource not found", HttpStatus.NOT_FOUND),
    UNDEFINED(997, "Error code is undefined", HttpStatus.INTERNAL_SERVER_ERROR),
    CANNOT_PARSE_DATA(998, "Cannot parse data", HttpStatus.BAD_REQUEST),
    COMMON_MESSAGE(999, "System error", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_EXISTED(1000, "User existed", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "Permission denied", HttpStatus.FORBIDDEN),
    SEND_EMAIL_ERROR(2000, "Send email error", HttpStatus.INTERNAL_SERVER_ERROR);

    int code;
    String message;
    HttpStatusCode httpStatusCode;
}
