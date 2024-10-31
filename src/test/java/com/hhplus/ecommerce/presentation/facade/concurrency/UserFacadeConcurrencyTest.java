package com.hhplus.ecommerce.presentation.facade.concurrency;

import com.hhplus.ecommerce.domain.User;
import com.hhplus.ecommerce.domain.repository.UserRepository;
import com.hhplus.ecommerce.presentation.dto.request.user.UserBalanceRequestDto;
import com.hhplus.ecommerce.presentation.dto.request.user.UserChargeRequestDto;
import com.hhplus.ecommerce.presentation.dto.request.user.UserUseRequestDto;
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
import java.util.concurrent.atomic.AtomicInteger;

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
    @DisplayName("한 사용자에게 동시에 n번의 충전을 했을때 한번만 성공 해야 된다. - 충전은 낙관적락")
    void chargeConcurrencyTest() throws Exception {
        long startTime = System.currentTimeMillis();
        //given
        int thread = 10;
        int balance = 10000;
        ExecutorService executorService = Executors.newFixedThreadPool(thread);
        CountDownLatch countDownLatch = new CountDownLatch(thread);

        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger failCount = new AtomicInteger(0);

        //when
        for (int i = 0; i < thread; i++) {
            executorService.submit(() -> {
                try {
                    UserChargeRequestDto requestDto = new UserChargeRequestDto(saveUser.getUserId(), balance);
                    userFacade.chargeUserBalance(requestDto);
                    successCount.incrementAndGet();
                } catch (Exception e) {
                    e.printStackTrace();
                    failCount.incrementAndGet();
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();

        //then
        UserBalanceRequestDto requestDto = new UserBalanceRequestDto(saveUser.getUserId());
        UserBalanceResponseDto responseDto = userFacade.getUserBalance(requestDto);
        assertEquals(1, successCount.get());
        assertEquals(9, failCount.get());
        assertEquals(saveUser.getBalance() + balance, responseDto.balance());
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println("포인트 충전 낙관적락 소요시간 ==> " + duration + "ms");
        executorService.shutdown();
    }

    @Test
    @DisplayName("한 사용자에게 n번의 포인트 사용이 이루어지고 한번만 성공해야된다. - 낙관적락")
    void useConcurrencyTest() throws Exception {
        long startTime = System.currentTimeMillis();
        //given
        int thread = 10;
        int balance = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(thread);
        CountDownLatch countDownLatch = new CountDownLatch(thread);

        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger failCount = new AtomicInteger(0);

        //when
        for (int i = 0; i < thread; i++) {
            executorService.submit(() -> {
                try {
                    UserUseRequestDto requestDto = new UserUseRequestDto(saveUser.getUserId(), balance);
                    userFacade.useUserBalance(requestDto);
                    successCount.incrementAndGet();
                } catch (Exception e) {
                    e.printStackTrace();
                    failCount.incrementAndGet();
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();

        //then
        UserBalanceRequestDto requestDto = new UserBalanceRequestDto(saveUser.getUserId());
        UserBalanceResponseDto responseDto = userFacade.getUserBalance(requestDto);
        assertEquals(1, successCount.get());
        assertEquals(9, failCount.get());
        assertEquals(responseDto.balance(), saveUser.getBalance() - balance);

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println("포인트 사용 낙관적락 소요시간 ==> " + duration + "ms");
        executorService.shutdown();
    }
}