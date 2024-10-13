package com.hhplus.ecommerce.business;

import com.hhplus.ecommerce.domain.Basket;
import com.hhplus.ecommerce.domain.User;
import com.hhplus.ecommerce.infrastructure.BasketRepository;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BasketServiceTest {

    @Mock
    private BasketRepository basketRepository;

    @InjectMocks
    private BasketService basketService;

    private User mockUser;

    @BeforeEach
    void setUp() {
        reset(basketRepository);
        mockUser = new User(1L, "김태현", 0, LocalDateTime.now());
    }

    @Test
    @DisplayName("장바구니_생성")
    void putInBasket() {
        //given
        Basket mockBasket = new Basket(1L, mockUser, LocalDateTime.now());
        when(basketRepository.save(mockBasket)).thenReturn(mockBasket);
        //when
        Basket resultBasket = basketService.save(mockBasket);
        //then
        assertNotNull(resultBasket);
        assertEquals(mockBasket.getBasketId(), resultBasket.getBasketId());
        assertEquals(mockBasket.getBasketUser().getUserId(), resultBasket.getBasketUser().getUserId());
    }

    @Test
    @DisplayName("장바구니_삭제")
    void deleteBasket() {
        // given
        Basket mockBasket = new Basket(1L, mockUser, LocalDateTime.now());
        when(basketRepository.findById(mockBasket.getBasketId())).thenReturn(Optional.of(mockBasket));
        // when
        basketService.delete(1L);
        //then
        verify(basketRepository, times(1)).delete(mockBasket);
    }

    @Test
    @DisplayName("장바구니_조회")
    void getBasket() {
        //given
        Basket mockBasket = new Basket(1L, mockUser, LocalDateTime.now());
        when(basketRepository.findById(mockBasket.getBasketId())).thenReturn(Optional.of(mockBasket));
        //when
        Basket resultBasket = basketService.getBasket(mockBasket.getBasketId());
        //then
        assertNotNull(resultBasket);
        assertEquals(mockBasket.getBasketId(), resultBasket.getBasketId());
    }
}