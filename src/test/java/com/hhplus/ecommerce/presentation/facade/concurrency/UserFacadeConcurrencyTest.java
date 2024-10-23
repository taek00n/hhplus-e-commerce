package com.hhplus.ecommerce.presentation.facade.concurrency;

import com.hhplus.ecommerce.domain.User;
import com.hhplus.ecommerce.domain.repository.UserRepository;
import com.hhplus.ecommerce.presentation.dto.request.user.UserBalanceRequestDto;
import com.hhplus.ecommerce.presentation.dto.request.user.UserChargeRequestDto;
import com.hhplus.ecommerce.presentation.dto.response.user.UserBalanceResponseDto;
import com.hhplus.ecommerce.presentation.facade.UserFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class UserFacadeConcurrencyTest {

    @Autowired
    private UserFacade userFacade;

    @Autowired
    private UserRepository userRepository;

    private User saveUser;

    @BeforeEach
    @Transactional
    void setUp() {
        saveUser = userRepository.save(new User("김태현", 200, LocalDateTime.now()));
    }

    @Test
    @DisplayName("한사용자에게 n번 충전 시켰을떄 충전한 값이랑 사용자의 총 잔액이랑 같은지 동시성 테스트")
    void concurrencyTest() throws Exception {
        //given
        int thread = 10;
        int balance = 10000;
        ExecutorService executorService = Executors.newFixedThreadPool(thread);
        CountDownLatch countDownLatch = new CountDownLatch(thread);

        //when
        for (int i = 0; i < thread; i++) {
            executorService.submit(() -> {
                try {
                    System.out.println("saveUser" + saveUser.getUserId());
                    UserChargeRequestDto requestDto = new UserChargeRequestDto(saveUser.getUserId(), balance);
                    userFacade.chargeUserBalance(requestDto);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();

        //then
        UserBalanceRequestDto requestDto = new UserBalanceRequestDto(saveUser.getUserId());
        UserBalanceResponseDto responseDto = userFacade.getUserBalance(requestDto);
        assertEquals(saveUser.getBalance() + (thread * balance), responseDto.balance());

        executorService.shutdown();
    }
}