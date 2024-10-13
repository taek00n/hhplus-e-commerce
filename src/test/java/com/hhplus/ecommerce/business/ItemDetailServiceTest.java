package com.hhplus.ecommerce.business;

import com.hhplus.ecommerce.domain.Item;
import com.hhplus.ecommerce.domain.ItemDetail;
import com.hhplus.ecommerce.infrastructure.ItemDetailRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ItemDetailServiceTest {

    @Mock
    private ItemDetailRepository itemDetailRepository;

    @InjectMocks
    private ItemDetailService itemDetailService;


    @Test
    @DisplayName("상품_상세_등록")
    void saveItemDetail() {
        //given
        Item mockItem1 = new Item(1L, "후드티", LocalDateTime.now());
        ItemDetail mockItemDetail1 = new ItemDetail(1L, mockItem1, "후드티A", 50000, 4);
        //when

        //then
    }
}