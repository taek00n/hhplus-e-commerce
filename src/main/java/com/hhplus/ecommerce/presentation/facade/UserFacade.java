package com.hhplus.ecommerce.presentation.facade;

import com.hhplus.ecommerce.application.UserService;
import com.hhplus.ecommerce.domain.User;
import com.hhplus.ecommerce.presentation.dto.request.user.UserBalanceRequestDto;
import com.hhplus.ecommerce.presentation.dto.request.user.UserChargeRequestDto;
import com.hhplus.ecommerce.presentation.dto.response.user.UserBalanceResponseDto;
import com.hhplus.ecommerce.presentation.dto.response.user.UserChargeResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserFacade {

    private final UserService userService;

    public UserBalanceResponseDto getUserBalance(UserBalanceRequestDto requestDto) {

        User user = userService.getUserByUserId(requestDto.userId());

        return new UserBalanceResponseDto(user.getUserId(), user.getBalance());
    }

    public UserChargeResponseDto chargeUserBalance(UserChargeRequestDto requestDto) {

        User user = userService.chargeUserBalance(requestDto.userId(), requestDto.balance());

        return new UserChargeResponseDto(user.getUserId(), requestDto.balance(), user.getBalance());
    }
}
