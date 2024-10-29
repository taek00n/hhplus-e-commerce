package com.hhplus.ecommerce.presentation.facade;

import com.hhplus.ecommerce.application.UserService;
import com.hhplus.ecommerce.domain.User;
import com.hhplus.ecommerce.presentation.dto.request.user.UserBalanceRequestDto;
import com.hhplus.ecommerce.presentation.dto.request.user.UserChargeRequestDto;
import com.hhplus.ecommerce.presentation.dto.response.user.UserBalanceResponseDto;
import com.hhplus.ecommerce.presentation.dto.response.user.UserChargeResponseDto;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserFacadeTest {

    @Autowired
    private UserFacade userFacade;

    @Autowired
    private UserService userService;

    @Test
    @DisplayName("사용자_잔액_확인")
    void getUserBalance() {
        //given
        User saveUser = userService.createUser(new User("김태현", 3999, LocalDateTime.now()));
        //when
        UserBalanceResponseDto responseDto = userFacade.getUserBalance(new UserBalanceRequestDto(saveUser.getUserId()));
        //then
        assertNotNull(responseDto);
        assertEquals(responseDto.userId(), saveUser.getUserId());
        assertEquals(responseDto.balance(), saveUser.getBalance());
    }

    @Test
    @DisplayName("사용자_금액_충전")
    void chargeUserBalance() {
        //given
        User saveUser = userService.createUser(new User("김태현", 3999, LocalDateTime.now()));
        //when
        UserChargeResponseDto responseDto = userFacade.chargeUserBalance(new UserChargeRequestDto(saveUser.getUserId(), 4000));
        //then
        assertNotNull(responseDto);
        assertEquals(responseDto.userId(), saveUser.getUserId());
        assertEquals(responseDto.balance(), 4000);
        assertEquals(responseDto.totalBalance(), 7999);
    }
}