package com.hhplus.ecommerce.business;

import com.hhplus.ecommerce.domain.Item;
import com.hhplus.ecommerce.infrastructure.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemService itemService;

    @BeforeEach
    void setUp() {
        reset(itemRepository);
    }

    @Test
    @DisplayName("상품_등록")
    void saveItem() {
        //given
        Item mockItem = new Item(1L, "후드티", LocalDateTime.now());
        when(itemRepository.save(mockItem)).thenReturn(mockItem);
        //when
        Item resultItem = itemService.save(mockItem);
        //then
        assertNotNull(resultItem);
        assertEquals(mockItem.getItemId(), resultItem.getItemId());
        assertEquals(mockItem.getItemName(), resultItem.getItemName());
    }

    @Test
    @DisplayName("전체_상품_조회")
    void getAllItems() {
        //given
        Item mockItem1 = new Item(1L, "후드티", LocalDateTime.now());
        Item mockItem2 = new Item(2L, "청바지", LocalDateTime.now());
        Item mockItem3 = new Item(3L, "맨투맨", LocalDateTime.now());
        //when

        //then
    }

}