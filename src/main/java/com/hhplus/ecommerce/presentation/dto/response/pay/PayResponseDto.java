package com.hhplus.ecommerce.presentation.dto.response.pay;

public record PayResponseDto (
        boolean isSuccess,
        String msg
){
    public static PayResponseDto payFacadeResponseDto(boolean isSuccess, String msg) {
        return new PayResponseDto(isSuccess, msg);
    }
}
