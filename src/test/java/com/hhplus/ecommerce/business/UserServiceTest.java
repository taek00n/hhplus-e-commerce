package com.hhplus.ecommerce.business;

import com.hhplus.ecommerce.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;

    private User saveUser;
    private Long userId;

    @BeforeEach
    void setUp() {
        saveUser = userService.createUser(new User("김태현", 0, LocalDateTime.now()));
        userId = saveUser.getUserId();
    }

    @Test
    @DisplayName("등록되지않은_사용자_조회")
    void getUser() {
        //given
        Long testId = 526L;
        //when then
        assertThrows(IllegalArgumentException.class, () -> userService.getUser(testId));
    }

    @Test
    @DisplayName("사용자_잔액_조회")
    void getBalance() {
        //given
        User resultUser = userService.getUser(userId);
        // when then
        assertEquals(saveUser.getBalance(), resultUser.getBalance());
    }

    @Test
    @DisplayName("사용자_잔액_충전")
    void chargeBalance() {
        //given
        int chargeBalance = 20000;
        User user = userService.getUser(userId);
        //when
        User resultUser = userService.chargeUserBalance(user.getUserId(), chargeBalance);
        //then
        assertEquals(saveUser.getBalance(), resultUser.getBalance());
    }
}