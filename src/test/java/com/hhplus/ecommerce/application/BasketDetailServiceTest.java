package com.hhplus.ecommerce.application;

import com.hhplus.ecommerce.domain.Basket;
import com.hhplus.ecommerce.domain.BasketDetail;
import com.hhplus.ecommerce.domain.Item;
import com.hhplus.ecommerce.domain.User;
import com.hhplus.ecommerce.domain.repository.BasketRepository;
import com.hhplus.ecommerce.domain.repository.ItemRepository;
import com.hhplus.ecommerce.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
class BasketDetailServiceTest {

    @Autowired
    private BasketDetailService basketDetailService;

    @Autowired
    private BasketRepository basketJpaRepository;

    @Autowired
    private ItemRepository itemJpaRepository;

    @Autowired
    private UserRepository userRepository;

    private Basket saveBasket;
    private Item saveItem1;
    private Item saveItem2;
    private User saveUser;

    @Autowired
    private BasketService basketService;

    @BeforeEach
    void setUp() {
        saveUser = userRepository.save(new User("김태현", 0, LocalDateTime.now()));
        saveItem1 = itemJpaRepository.save(new Item("청바지A", 9999, 4, LocalDateTime.now()));
        saveItem2 = itemJpaRepository.save(new Item("청바지B", 9999, 2, LocalDateTime.now()));
    }

    @Test
    @DisplayName("상품_추가")
    void addItemInBasketDetail() {
        // given
        Basket basket = new Basket(saveUser, LocalDateTime.now());
        BasketDetail basketDetail = new BasketDetail(basket, saveItem1, 1);
        // when
        BasketDetail saveBasketDetail = basketDetailService.createBasketDetail(basketDetail);
        //then
        assertNotNull(saveBasketDetail);
        assertEquals(saveBasketDetail.getBasket(), basket);
    }
}