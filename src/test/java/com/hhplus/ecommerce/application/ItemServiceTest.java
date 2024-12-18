package com.hhplus.ecommerce.application;

import com.hhplus.ecommerce.common.exception.RestApiException;
import com.hhplus.ecommerce.domain.Item;
import com.hhplus.ecommerce.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ItemServiceTest {

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    private User saveUser;
    private Item saveItem;
    private Long itemId;

    @BeforeEach
    void setUp() {
        saveItem = itemService.createItem(new Item("청바지", 50000, 5));
        itemId = saveItem.getItemId();
        saveUser = userService.createUser(new User("김태현", 0));
    }

    @Test
    @DisplayName("단일_상품_조회")
    void getItem() {
        //when
        Item resultItem = itemService.getItemByItemId(itemId);
        //then
        assertEquals(saveItem.getItemId(), resultItem.getItemId());
        assertEquals(saveItem.getItemPrice(), resultItem.getItemPrice());
        assertEquals(saveItem.getItemPrice(), resultItem.getItemPrice());
    }

    @Test
    @DisplayName("단일_상품_조회_없을떄")
    void getNoneItem() {
        //given
        Long searchItemId = itemId + 1L;
        //when then
        assertThrows(RestApiException.class, () -> itemService.getItemByItemId(searchItemId));
    }

    @Test
    @DisplayName("전체_상품_조회")
    void getItems() {
        //given
        Item item1 = new Item("맨투맨", 30000, 59);
        Item item2 = new Item("후드티", 30000, 59);
        Item item3 = new Item("슬랙스", 30000, 59);
        itemService.createItem(item1);
        itemService.createItem(item2);
        itemService.createItem(item3);
        //when
        List<Item> resultItems = itemService.getItemAll();
        //then
        assertEquals(4, resultItems.size());
    }
}