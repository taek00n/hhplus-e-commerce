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
    NOT_EQUALS_USER(HttpStatus.BAD_REQUEST, "다른 사용자의 주문은 취소할 수 없습니다.."),
    NOT_EQUALS_STATUS(HttpStatus.BAD_REQUEST, "해당 주문의 주문상태를 확인해주세요.");

    private final HttpStatus httpStatus;
    private final String message;
}