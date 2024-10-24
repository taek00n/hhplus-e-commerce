package com.hhplus.ecommerce.common.exception.domain;

import com.hhplus.ecommerce.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {

    NO_USER_BY_ID(HttpStatus.NOT_FOUND, "고객이 존재하지 않습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}