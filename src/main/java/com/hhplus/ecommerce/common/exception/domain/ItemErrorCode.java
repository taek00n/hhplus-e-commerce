package com.hhplus.ecommerce.common.exception.domain;

import com.hhplus.ecommerce.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ItemErrorCode implements ErrorCode {


    NO_ITEM_BY_ID(HttpStatus.NOT_FOUND, "상품번호에 해당하는 상품이 없습니다."),
    ITEM_SOLD_OUT(HttpStatus.OK, "상품이 품절되었습니다."),
    NO_ENOUGH_ITEM(HttpStatus.NOT_FOUND, "수량이 충분하지 않습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String message;
}