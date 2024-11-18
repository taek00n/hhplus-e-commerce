package com.hhplus.ecommerce.application;

import com.hhplus.ecommerce.common.exception.RestApiException;
import com.hhplus.ecommerce.domain.Basket;
import com.hhplus.ecommerce.domain.User;
import com.hhplus.ecommerce.domain.repository.UserRepository;
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
class BasketServiceTest {

    @Autowired
    private BasketService basketService;

    @Autowired
    private UserRepository userRepository;


    private User saveUser;
    private Basket saveBasket;

    @BeforeEach
    void setUp() {
        saveUser = userRepository.save(new User("김태현", 0));
        saveBasket = basketService.createBasket(new Basket(saveUser));
    }

    @Test
    @DisplayName("장바구니_생성")
    void createBasket() {
        //given
        Basket basket = new Basket(saveUser);
        //when
        Basket saveBasket = basketService.createBasket(basket);
        //then
        assertNotNull(saveBasket);
        assertEquals(saveBasket.getBasketUser(), saveUser);
    }

    @Test
    @DisplayName("없는_장바구니_번호로_조회")
    void getNoneBasketByBasketId() {
        //given
        Long basketId = saveBasket.getBasketId() + 1L;
        //when then
        assertThrows(RestApiException.class, () -> basketService.getBasket(basketId));
    }

    @Test
    @DisplayName("장바구니_번호로_조회")
    void getBasketByBasketId() {
        //when
        Basket resultBasket = basketService.getBasket(saveBasket.getBasketId());
        //then
        assertNotNull(resultBasket);
        assertEquals(resultBasket.getBasketId(), saveBasket.getBasketId());
        assertEquals(saveBasket.getBasketUser(), saveUser);
    }

    @Test
    @DisplayName("사용자번호로_장바구니_조회")
    void getBasketByUserId() {
        //when
        Basket userBasket = basketService.getUserBasket(saveUser.getUserId());
        //then
        assertNotNull(userBasket);
        assertEquals(userBasket.getBasketUser(), saveUser);
        assertEquals(userBasket.getBasketId(), saveBasket.getBasketId());
    }
}