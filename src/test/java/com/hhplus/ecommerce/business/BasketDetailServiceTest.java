package com.hhplus.ecommerce.business;

import com.hhplus.ecommerce.domain.*;
import com.hhplus.ecommerce.infrastructure.BasketDetailRepository;
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
class BasketDetailServiceTest {

    @Mock
    private BasketDetailRepository basketDetailRepository;

    @InjectMocks
    private BasketDetailService basketDetailService;

    private User mockUser;
    private Basket mockBasket;
    private Item mockItem;
    private ItemDetail mockItemDetail;

    @BeforeEach
    void setUp() {
        reset(basketDetailRepository);
        mockUser = new User(1L, "김태현", 0, LocalDateTime.now());
        mockBasket = new Basket(1L, mockUser, LocalDateTime.now());
        mockItem = new Item(1L, "청바지", LocalDateTime.now());
        mockItemDetail = new ItemDetail(1L, mockItem, "청바지정상", 5000, 4);
    }

    @Test
    @DisplayName("상품_장바구니에_담기")
    void saveBasketDetail() {
        //given
        BasketDetail mockBasketDetail = new BasketDetail(1L, mockBasket, mockItemDetail, 1);
        when(basketDetailRepository.save(mockBasketDetail)).thenReturn(mockBasketDetail);
        //when
        BasketDetail resultBasketDetail = basketDetailService.save(mockBasketDetail);
        //then
        assertNotNull(resultBasketDetail);
        assertEquals(mockBasketDetail.getBasketDetailId(), resultBasketDetail.getBasketDetailId());
    }

    @Test
    @DisplayName("상품_장바구니에서_제거")
    void deleteBasketDetail() {
        //given
        BasketDetail mockBasketDetail = new BasketDetail(1L, mockBasket, mockItemDetail, 1);
        when(basketDetailRepository.findById(mockBasketDetail.getBasketDetailId())).thenReturn(Optional.of(mockBasketDetail));
        //when
        basketDetailService.delete(mockBasketDetail.getBasketDetailId());
        //then
        verify(basketDetailRepository, times(1)).delete(mockBasketDetail);
    }

    @Test
    @DisplayName("장바구니_상세_조회")
    void getBasketDetail() {
        //given
        BasketDetail mockBasketDetail = new BasketDetail(1L, mockBasket, mockItemDetail, 1);
        when(basketDetailRepository.findById(mockBasketDetail.getBasketDetailId())).thenReturn(Optional.of(mockBasketDetail));
        //when
        BasketDetail resultBastDetail = basketDetailService.findById(mockBasketDetail.getBasketDetailId());
        //then
        assertNotNull(resultBastDetail);
        assertEquals(mockBasketDetail.getBasketDetailId(), resultBastDetail.getBasketDetailId());
    }
}