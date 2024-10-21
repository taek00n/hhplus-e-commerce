package com.hhplus.ecommerce.common.exception.domain;

import com.hhplus.ecommerce.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum BasketErrorCode implements ErrorCode {

    NO_BASKET_BY_ID(HttpStatus.NOT_FOUND, "장바구니가 존재하지 않습니다."),
    NO_BASKET_DETAIL_BY_ID(HttpStatus.NOT_FOUND, "상세 상품이 존재하지 않습니다."),
    OVER_ITEM_AMOUNT(HttpStatus.OK, "한 상품은 최대 10개까지 담을수 있습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
