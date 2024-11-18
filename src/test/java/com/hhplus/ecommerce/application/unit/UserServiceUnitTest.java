package com.hhplus.ecommerce.application.unit;

import com.hhplus.ecommerce.application.UserService;
import com.hhplus.ecommerce.domain.User;
import com.hhplus.ecommerce.domain.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceUnitTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        reset(userRepository);
    }

    private final long userId = 1L;

    @Test
    @DisplayName("사용자_등록")
    void saveUser() {
        //given
        User mockUser = new User("김태현", 0);
        when(userRepository.save(mockUser)).thenReturn(mockUser);
        //when
        User resultUser = userService.createUser(mockUser);
        //then
        assertNotNull(resultUser);
        assertEquals(mockUser.getUserId(), resultUser.getUserId());
    }

    @Test
    @DisplayName("사용자_조회")
    void getUser() {
        //given
        int balance = 2000;
        when(userRepository.findByUserId(userId)).thenReturn(Optional.of(new User("김태현", balance)));
        //when
        User resultUser = userService.getUserByUserId(userId);
        //then
        assertNotNull(resultUser);
        assertEquals(balance, resultUser.getBalance());
    }

    @Test
    @DisplayName("없는 사용자 조회 시 error 확인")
    void getNoneUser() {
        //given
        when(userRepository.findByUserId(1L)).thenThrow(new RuntimeException());
        assertThrows(RuntimeException.class, () -> userService.getUserByUserId(1L));
    }

    @Test
    @DisplayName("포인트_충전")
    void chargeBalance() {
        //given
        int chargeBalance = 3000;
        User mockUser = new User("김태현", 0);
        when(userRepository.findByUserId(1L)).thenReturn(Optional.of(mockUser));
        //when
        User resultUser = userService.chargeUserBalance(1L, chargeBalance);
        //then
        assertNotNull(resultUser);
        assertEquals(mockUser.getUserId(), resultUser.getUserId());
        assertEquals(mockUser.getBalance(), resultUser.getBalance());
    }

    @Test
    @DisplayName("포인트_사용")
    void useBalance() {
        //given
        int useBalance = 3000;
        User mockUser = new User("김태현", 5000);
        when(userRepository.findByUserId(1L)).thenReturn(Optional.of(mockUser));
        //when
        User resultUser = userService.useUserBalance(1L, useBalance);
        //then
        assertNotNull(resultUser);
        assertEquals(mockUser.getUserId(), resultUser.getUserId());
        assertEquals(mockUser.getBalance(), resultUser.getBalance());
    }
}