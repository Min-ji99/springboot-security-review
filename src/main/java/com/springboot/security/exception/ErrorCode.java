package com.springboot.security.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    DUPLICATED_USER_NAME(HttpStatus.CONFLICT, "존재하는 username 입니다"),
    NOTFOUND_USER_NAME(HttpStatus.NOT_FOUND, "존재하는 username이 없습니다"),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "Password not correct");;

    private HttpStatus httpStatus;
    private String message;
}
