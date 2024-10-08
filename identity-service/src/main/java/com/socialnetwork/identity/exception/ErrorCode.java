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
    BIRTHDAY_INVALID(1011, "Your age is at least {min}", HttpStatus.BAD_REQUEST),
    PERMISSION_NAME_INVALID(1012, "Permission name invalid", HttpStatus.BAD_REQUEST),
    ROLE_NAME_INVALID(1012, "Role name invalid", HttpStatus.BAD_REQUEST),
    FIRST_NAME_INVALID(1113, "First name invalid", HttpStatus.BAD_REQUEST),
    LAST_NAME_INVALID(1114, "Last name invalid", HttpStatus.BAD_REQUEST),
    CREATE_USER_PROFILE_ERROR(1115, "Create user profile error", HttpStatus.INTERNAL_SERVER_ERROR),
    LOGIN_WITH_GOOGLE_ACCOUNT_ERROR(1116, "Login with Google account error", HttpStatus.INTERNAL_SERVER_ERROR),
    DISCOUNT_USED(1118, "Discount is used", HttpStatus.BAD_REQUEST),
    DISCOUNT_INVALID(1117, "Discount invalid", HttpStatus.BAD_REQUEST);

    int code;
    String message;
    HttpStatusCode httpStatusCode;
}
