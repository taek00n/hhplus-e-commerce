package com.hhplus.ecommerce.common.exception.domain;

import com.hhplus.ecommerce.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum OrderErrorCode implements ErrorCode {

    NO_ORDER_BY_ID(HttpStatus.NOT_FOUND, "주문이 존재하지 않습니다."),
    NO_DETAIL_BY_ORDER(HttpStatus.BAD_REQUEST, "주문의 상세가 존재하지 않습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}