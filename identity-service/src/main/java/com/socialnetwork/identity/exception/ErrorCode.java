package com.socialnetwork.identity.exception;

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
    USER_NOT_FOUND(1001, "User not found", HttpStatus.BAD_REQUEST),
    USERNAME_INPUT_INVALID(1002, "Username must be greater than {min} and less than {max} characters", HttpStatus.BAD_REQUEST),
    PASSWORD_INPUT_INVALID(1003, "Password must be greater than {min} characters", HttpStatus.BAD_REQUEST),
    USERNAME_OR_PASSWORD_INVALID(1004, "Username or password invalid", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "Permission denied", HttpStatus.FORBIDDEN),
    PERMISSION_EXISTED(1008, "Permission existed", HttpStatus.BAD_REQUEST),
    ROLE_EXISTED(1009, "Role existed", HttpStatus.BAD_REQUEST),
    PERMISSION_NOT_FOUND(1010, "Permission not found", HttpStatus.BAD_REQUEST),
    ROLE_NOT_FOUND(1011, "Role not found", HttpStatus.BAD_REQUEST),
    BIRTHDAY_INVALID(1011, "Your age is at least {min}", HttpStatus.BAD_REQUEST);

    int code;
    String message;
    HttpStatusCode httpStatusCode;
}
