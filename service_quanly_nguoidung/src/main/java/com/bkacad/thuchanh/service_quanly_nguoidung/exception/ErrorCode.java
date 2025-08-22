package com.bkacad.thuchanh.service_quanly_nguoidung.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    // General & User

    USERNAME_EXISTED(1004, "User already existed", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(1003, "không tìm thấy người dùng", HttpStatus.BAD_REQUEST),
    UNCATEGORIZED_EXCEPTION(9999, "UNCATEGORIZED_EXCEPTION", HttpStatus.BAD_REQUEST),
    EMAIL_EXISTED(1004, "Email already exists", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATION(1005, "Unauthenticatin", HttpStatus.BAD_REQUEST);


    private final int errorCode;
    private final String errorMessage;
    private final HttpStatus httpStatus;

    ErrorCode(int errorCode, String errorMessage, HttpStatus httpStatus) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }

    // Overload for enums without errorCode (for compatibility)
    ErrorCode(HttpStatus status, String message) {
        this.errorCode = -1;
        this.errorMessage = message;
        this.httpStatus = status;
    }

    public String getErrorMessage(Object... args) {
        return String.format(errorMessage, args);
    }
}