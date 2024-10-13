package com.hhplus.ecommerce.business;

import com.hhplus.ecommerce.domain.Item;
import com.hhplus.ecommerce.domain.ItemDetail;
import com.hhplus.ecommerce.infrastructure.ItemDetailRepository;
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
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemDetailServiceTest {

    @Mock
    private ItemDetailRepository itemDetailRepository;

    @InjectMocks
    private ItemDetailService itemDetailService;

    private Item mockItem;

    @BeforeEach
    void setUp() {
        reset(itemDetailRepository);
        mockItem = new Item(1L, "청바지", LocalDateTime.now());
    }

    @Test
    @DisplayName("상품_상세_등록")
    void saveItemDetail() {
        //given
        ItemDetail mockItemDetail = new ItemDetail(1L, mockItem, "후드티A", 50000, 4);
        when(itemDetailRepository.save(mockItemDetail)).thenReturn(mockItemDetail);
        //when
        ItemDetail resultItem = itemDetailService.save(mockItemDetail);
        //then
        assertNotNull(resultItem);
        assertEquals(mockItemDetail.getItemDetailId(), resultItem.getItemDetailId());
    }

    @Test
    @DisplayName("상품_상세_조회")
    void findById() {
        //given
        ItemDetail mockItemDetail = new ItemDetail(1L, mockItem, "후드티A", 50000, 4);
        when(itemDetailRepository.findById(mockItemDetail.getItemDetailId())).thenReturn(Optional.of(mockItemDetail));
        //when
        ItemDetail resultItemDetail = itemDetailService.findById(mockItemDetail.getItemDetailId());
        //then
        assertNotNull(resultItemDetail);
        assertEquals(mockItemDetail.getItemDetailId(), resultItemDetail.getItemDetailId());
    }
}