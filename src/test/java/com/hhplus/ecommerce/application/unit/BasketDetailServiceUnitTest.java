package com.hhplus.ecommerce.application.unit;

import com.hhplus.ecommerce.application.BasketDetailService;
import com.hhplus.ecommerce.domain.*;
import com.hhplus.ecommerce.domain.repository.BasketDetailRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BasketDetailServiceUnitTest {

    @Mock
    private BasketDetailRepository basketDetailRepository;

    @InjectMocks
    private BasketDetailService basketDetailService;

    private User mockUser;
    private Basket mockBasket;
    private Item mockItem1;
    private Item mockItem2;

    @BeforeEach
    void setUp() {
        reset(basketDetailRepository);
        mockUser = new User("김태현", 0, LocalDateTime.now());
        mockBasket = new Basket(mockUser, LocalDateTime.now());
        mockItem1 = new Item("청바지1", 10000, 10, LocalDateTime.now());
        mockItem2 = new Item("청바지2", 20000, 20, LocalDateTime.now());
    }

    @Test
    @DisplayName("상품_장바구니에_담기")
    void saveBasketDetail() {
        //given
        BasketDetail mockBasketDetail = new BasketDetail(mockBasket, mockItem1, 1);
        when(basketDetailRepository.save(mockBasketDetail)).thenReturn(mockBasketDetail);
        //when
        BasketDetail resultBasketDetail = basketDetailService.createBasketDetail(mockBasketDetail);
        //then
        assertNotNull(resultBasketDetail);
        assertEquals(mockBasketDetail.getBasketDetailId(), resultBasketDetail.getBasketDetailId());
    }

    @Test
    @DisplayName("상품_장바구니에서_제거")
    void deleteBasketDetail() {
        //given
        BasketDetail mockBasketDetail = new BasketDetail(mockBasket, mockItem1, 1);
        when(basketDetailRepository.findById(mockBasketDetail.getBasketDetailId())).thenReturn(Optional.of(mockBasketDetail));
        //when
        basketDetailService.delete(mockBasketDetail.getBasketDetailId());
        //then
        verify(basketDetailRepository, times(1)).delete(mockBasketDetail);
    }

    @Test
    @DisplayName("장바구니에_아이템이_있는_확인")
    void findItemInBasketDetail() {
        //given
        BasketDetail mockBasketDetail = new BasketDetail(mockBasket, mockItem1, 1);
        when(basketDetailRepository.findByItem(mockItem1)).thenReturn(mockBasketDetail);
        //when
        BasketDetail resultBastDetail = basketDetailService.getBasketDetailByItem(mockItem1);
        //then
        assertNotNull(resultBastDetail);
        assertEquals(mockBasketDetail.getBasketDetailId(), resultBastDetail.getBasketDetailId());
    }

    @Test
    @DisplayName("장바구니번호로_상세_내역_전부_조회")
    void getAllItemInBasketDetail() {
        //given
        BasketDetail mockBasketDetail1 = new BasketDetail(mockBasket, mockItem1, 1);
        BasketDetail mockBasketDetail2 = new BasketDetail(mockBasket, mockItem2, 1);
        List<BasketDetail> mockBasketDetailList = new ArrayList<>();
        mockBasketDetailList.add(mockBasketDetail1);
        mockBasketDetailList.add(mockBasketDetail2);
        when(basketDetailRepository.findAllByBasket(mockBasket)).thenReturn(mockBasketDetailList);
        //when
        List<BasketDetail> resultBasketDetailList = basketDetailService.getAllDetailByBasket(mockBasket);
        // then
        assertNotNull(resultBasketDetailList);
        assertEquals(mockBasketDetailList.size(), resultBasketDetailList.size());
    }
}