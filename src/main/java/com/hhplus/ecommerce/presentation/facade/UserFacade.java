package com.hhplus.ecommerce.presentation.facade;

import com.hhplus.ecommerce.business.UserService;
import com.hhplus.ecommerce.domain.User;
import com.hhplus.ecommerce.presentation.dto.request.user.UserBalanceRequestDto;
import com.hhplus.ecommerce.presentation.dto.request.user.UserChargeRequestDto;
import com.hhplus.ecommerce.presentation.dto.response.user.UserBalanceResponseDto;
import com.hhplus.ecommerce.presentation.dto.response.user.UserChargeResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class UserFacade {

    private final UserService userService;

    public UserBalanceResponseDto getUserBalance(UserBalanceRequestDto requestDto) {

        User user = userService.getUser(requestDto.userId());

        return new UserBalanceResponseDto(user.getUserId(), user.getBalance());
    }

    public UserChargeResponseDto chargeBalance(UserChargeRequestDto requestDto) {

        User user = userService.getUser(requestDto.userId());
        user.chargeBalance(requestDto.balance());

        return new UserChargeResponseDto(user.getUserId(), requestDto.balance(), user.getBalance());
    }
}
