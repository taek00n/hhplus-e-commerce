package com.hhplus.ecommerce.application.unit;

import com.hhplus.ecommerce.application.BasketService;
import com.hhplus.ecommerce.domain.Basket;
import com.hhplus.ecommerce.domain.User;
import com.hhplus.ecommerce.domain.repository.BasketRepository;
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
class BasketServiceUnitTest {

    @Mock
    private BasketRepository basketRepository;

    @InjectMocks
    private BasketService basketService;

    private User mockUser;

    @BeforeEach
    void setUp() {
        reset(basketRepository);
        mockUser = new User("김태현", 0, LocalDateTime.now());
    }

    @Test
    @DisplayName("장바구니_생성")
    void createBasket() {
        //given
        Basket mockBasket = new Basket(mockUser, LocalDateTime.now());
        when(basketRepository.save(mockBasket)).thenReturn(mockBasket);
        //when
        Basket resultBasket = basketService.createBasket(mockBasket);
        //then
        assertNotNull(resultBasket);
        assertEquals(mockBasket.getBasketId(), resultBasket.getBasketId());
        assertEquals(mockBasket.getBasketUser().getUserId(), resultBasket.getBasketUser().getUserId());
    }

    @Test
    @DisplayName("장바구니_삭제")
    void deleteBasket() {
        // given
        Basket mockBasket = new Basket(mockUser, LocalDateTime.now());
        // when
        basketService.removeBasketByBasketId(1L);
        //then
        verify(basketRepository, times(1)).deleteByBasketId(1L);
    }

    @Test
    @DisplayName("장바구니_번호로_조회")
    void getBasket() {
        //given
        Basket mockBasket = new Basket(mockUser, LocalDateTime.now());
        when(basketRepository.findByBasketId(1L)).thenReturn(Optional.of(mockBasket));
        //when
        Basket resultBasket = basketService.getBasket(1L);
        //then
        assertNotNull(resultBasket);
        assertEquals(mockBasket.getBasketId(), resultBasket.getBasketId());
    }

    @Test
    @DisplayName("사용자의_장바구니_조회")
    void getUserBasket() {
        //given
        Basket mockBasket = new Basket(mockUser, LocalDateTime.now());
        when(basketRepository.getUserBasket(1L)).thenReturn(mockBasket);
        //when
        Basket resultBasket = basketService.getUserBasket(1L);
        //then
        assertNotNull(resultBasket);
        assertEquals(mockBasket.getBasketId(), resultBasket.getBasketId());
    }
}