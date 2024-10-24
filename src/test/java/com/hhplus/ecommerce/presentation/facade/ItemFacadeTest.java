package com.hhplus.ecommerce.presentation.facade;

import com.hhplus.ecommerce.business.ItemService;
import com.hhplus.ecommerce.business.UserService;
import com.hhplus.ecommerce.domain.Item;
import com.hhplus.ecommerce.domain.User;
import com.hhplus.ecommerce.presentation.dto.request.order.CreateOrderRequestDto;
import com.hhplus.ecommerce.presentation.dto.response.item.ItemResponseDto;
import com.hhplus.ecommerce.presentation.dto.response.item.ItemsResponseDto;
import com.hhplus.ecommerce.presentation.dto.response.item.PopularItemsResponseDto;
import com.hhplus.ecommerce.presentation.dto.response.order.CreateOrderResponseDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ItemFacadeTest {

    @Autowired
    private ItemFacade itemFacade;

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderFacade orderFacade;

    @Test
    @DisplayName("전체상품을 조회했을때 전체 상품의 정보를 반환해준다.")
    void getItemAll() {
        //given
        itemService.createItem(new Item("청바지1", 100000, 45, LocalDateTime.now()));
        itemService.createItem(new Item("청바지2", 200000, 55, LocalDateTime.now()));
        //when
        ItemsResponseDto responseDto = itemFacade.getItemAll();
        //then
        assertNotNull(responseDto);
        assertEquals(responseDto.items().size(), 2);
    }

    @Test
    @DisplayName("상품아이디로 조회시 해당 상품의 정보를 반환해준다.")
    void getItemByItemId() {
        //given
        Item saveItem = itemService.createItem(new Item("청바지1", 100000, 45, LocalDateTime.now()));
        //when
        ItemResponseDto responseDto = itemFacade.getItemByItemId(saveItem.getItemId());
        //then
        assertNotNull(responseDto);
        assertEquals(responseDto.itemId(), saveItem.getItemId());
        assertEquals(responseDto.itemName(), saveItem.getItemName());
    }

    @Test
    @DisplayName("인기 상품 조회시 최근 3일간의 판매량이 많은 TOP5를 반환해준다.")
    void getPopularItem() {
        //given
        Item saveItem = itemService.createItem(new Item("청바지1", 100000, 45, LocalDateTime.now()));
        User saveUser = userService.createUser(new User("김태현", 3999, LocalDateTime.now()));
        Map<Long, Integer> itemMap = new HashMap<>();
        itemMap.put(saveItem.getItemId(), 1);
        orderFacade.createOrder(new CreateOrderRequestDto(saveUser.getUserId(), itemMap));
        //when
        PopularItemsResponseDto responseDto = itemFacade.getPopularItem();
        //then
        assertNotNull(responseDto);
    }
}