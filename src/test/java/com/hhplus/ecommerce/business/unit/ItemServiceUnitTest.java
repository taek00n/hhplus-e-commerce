package com.hhplus.ecommerce.business.unit;

import com.hhplus.ecommerce.business.ItemService;
import com.hhplus.ecommerce.domain.Item;
import com.hhplus.ecommerce.domain.repository.ItemRepository;
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
class ItemServiceUnitTest {

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
        Item mockItem = new Item("후드티", 50000, 10, LocalDateTime.now());
        when(itemRepository.save(mockItem)).thenReturn(mockItem);
        //when
        Item resultItem = itemService.createItem(mockItem);
        //then
        assertNotNull(resultItem);
        assertEquals(mockItem.getItemId(), resultItem.getItemId());
        assertEquals(mockItem.getItemName(), resultItem.getItemName());
    }

    @Test
    @DisplayName("전체_상품_조회")
    void getItems() {
        //given
        List<Item> mockItemList = new ArrayList<>();
        Item mockItem1 = new Item("후드티", 50000, 10, LocalDateTime.now());
        Item mockItem2 = new Item("청바지", 56000, 16, LocalDateTime.now());
        Item mockItem3 = new Item("맨투맨", 58000, 13, LocalDateTime.now());
        mockItemList.add(mockItem1);
        mockItemList.add(mockItem2);
        mockItemList.add(mockItem3);
        when(itemRepository.findAll()).thenReturn(mockItemList);
        //when
        List<Item> resultItemList = itemService.getItemAll();
        //then
        assertNotNull(resultItemList);
        assertEquals(mockItemList.size(), resultItemList.size());
    }

    @Test
    @DisplayName("특정_상품_조회")
    void getItemById() {
        //given
        Item mockItem = new Item("후드티", 50000, 10, LocalDateTime.now());
        when(itemRepository.findByItemId(1L)).thenReturn(Optional.of(mockItem));
        //when
        Item resultItem = itemService.getItemByItemId(1L);
        //then
        assertNotNull(resultItem);
        assertEquals(mockItem.getItemId(), resultItem.getItemId());
    }
}