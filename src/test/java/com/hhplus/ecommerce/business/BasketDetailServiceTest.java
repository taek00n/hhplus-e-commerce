package com.hhplus.ecommerce.business;

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
        List<BasketDetail> basketDetails = new ArrayList<>();
        BasketDetail basketDetail = new BasketDetail(basket, saveItem1, 2);
        basketDetails.add(basketDetail);
        for (BasketDetail detail : basketDetails) {
            basket.addBasketDetail(detail);
        }
        // when
        Basket saveBasket = basketJpaRepository.save(basket);
        //then
        assertNotNull(saveBasket);
        assertEquals(saveBasket.getBasketDetails().size(), basketDetails.size());
    }

    @Test
    @DisplayName("상품_삭제")
    void deleteItemInBasketDetail() {
        //given
        Basket settingBasket = settingBasket();
        Long basketDetailId = settingBasket.getBasketDetails().get(0).getBasketDetailId();
        //when
        BasketDetail deleteBasketDetail = basketDetailService.getBasketDetail(basketDetailId);
        settingBasket.getBasketDetails().remove(deleteBasketDetail);
        //then
        assertEquals(1, settingBasket.getBasketDetails().size());
    }

    private Basket settingBasket() {
        Basket basket = new Basket(saveUser, LocalDateTime.now());
        List<BasketDetail> basketDetails = new ArrayList<>();

        BasketDetail basketDetail1 = new BasketDetail(basket, saveItem1, 2);
        BasketDetail basketDetail2 = new BasketDetail(basket, saveItem2, 2);
        basketDetails.add(basketDetail1);
        basketDetails.add(basketDetail2);

        for (BasketDetail detail : basketDetails) {
            basket.addBasketDetail(detail);
        }

        return basketJpaRepository.save(basket);
    }
}