package com.hhplus.ecommerce.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RestApiException extends Throwable {

    private final ErrorCode errorCode;
}
