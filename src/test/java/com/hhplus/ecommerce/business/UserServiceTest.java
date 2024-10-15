package com.hhplus.ecommerce.business;

import com.hhplus.ecommerce.domain.User;
import com.hhplus.ecommerce.infrastructure.UserRepository;
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
class UserServiceTest {

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
        User mockUser = new User("김태현", 0, LocalDateTime.now());
        when(userRepository.createUser(mockUser)).thenReturn(mockUser);
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
        when(userRepository.getUser(userId)).thenReturn(Optional.of(new User("김태현", balance, LocalDateTime.now())));
        //when
        User resultUser = userService.getUser(userId);
        //then
        assertNotNull(resultUser);
        assertEquals(balance, resultUser.getBalance());
    }

    @Test
    @DisplayName("없는_사용자_조회")
    void getNoneUser() {
        //given
        when(userRepository.getUser(1L)).thenThrow(new IllegalArgumentException());
        assertThrows(IllegalArgumentException.class, () -> userService.getUser(1L));
    }

    @Test
    @DisplayName("포인트_충전")
    void chargeBalance() {
        //given
        int chargeBalance = 3000;
        User mockUser = new User("김태현", 0, LocalDateTime.now());
        when(userRepository.findById(mockUser.getUserId())).thenReturn(Optional.of(mockUser));
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
        User mockUser = new User("김태현", 5000, LocalDateTime.now());
        when(userRepository.findById(mockUser.getUserId())).thenReturn(Optional.of(mockUser));
        //when
        User resultUser = userService.useUserBalance(mockUser.getUserId(), useBalance);
        //then
        assertNotNull(resultUser);
        assertEquals(mockUser.getUserId(), resultUser.getUserId());
        assertEquals(mockUser.getBalance(), resultUser.getBalance());
    }
}