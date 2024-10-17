package com.hhplus.ecommerce.presentation.facade;

import com.hhplus.ecommerce.business.ItemService;
import com.hhplus.ecommerce.business.UserService;
import com.hhplus.ecommerce.domain.Item;
import com.hhplus.ecommerce.domain.User;
import com.hhplus.ecommerce.presentation.dto.request.basket.AddBasketRequestDto;
import com.hhplus.ecommerce.presentation.dto.request.basket.BasketRequestDto;
import com.hhplus.ecommerce.presentation.dto.request.basket.DeleteBasketDetailRequestDto;
import com.hhplus.ecommerce.presentation.dto.response.basket.AddBasketResponseDto;
import com.hhplus.ecommerce.presentation.dto.response.basket.BasketResponseDto;
import com.hhplus.ecommerce.presentation.dto.response.basket.DeleteBasketDetailResponseDto;
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
class BasketFacadeTest {

    @Autowired
    private BasketFacade basketFacade;

    @Autowired
    private UserService userService;

    @Autowired
    private ItemService itemService;

    @Test
    @DisplayName("장바구니 추가")
    void addBasket() {
        //given
        User saveUser = userService.createUser(new User("김태현", 3999, LocalDateTime.now()));
        Item saveItem = itemService.saveItem(new Item("후드티", 30000, 4, LocalDateTime.now()));
        Map<Long, Integer> itemMap = new HashMap<>();
        itemMap.put(saveItem.getItemId(), 1);
        //when
        AddBasketResponseDto responseDto = basketFacade.addBasket(new AddBasketRequestDto(saveUser.getUserId(), itemMap));
        //then
        assertNotNull(responseDto);
    }

    @Test
    @DisplayName("장바구니 상품 제거")
    void deleteBasketDetail() {
        //given
        User saveUser = userService.createUser(new User("김태현", 3999, LocalDateTime.now()));
        Item saveItem = itemService.saveItem(new Item("후드티", 30000, 4, LocalDateTime.now()));
        Map<Long, Integer> itemMap = new HashMap<>();
        itemMap.put(saveItem.getItemId(), 1);
        basketFacade.addBasket(new AddBasketRequestDto(saveUser.getUserId(), itemMap));
        //when
        DeleteBasketDetailResponseDto responseDto = basketFacade.deleteBasketDetail(new DeleteBasketDetailRequestDto(saveUser.getUserId(), saveItem.getItemId()));
        //then
        assertNotNull(responseDto);
        assertEquals(responseDto.itemId(), saveItem.getItemId());
        assertEquals(responseDto.itemName(), saveItem.getItemName());
    }

    @Test
    @DisplayName("장바구니_상세_조회")
    void getBasket() {
        //given
        User saveUser = userService.createUser(new User("김태현", 3999, LocalDateTime.now()));
        Item saveItem = itemService.saveItem(new Item("후드티", 30000, 4, LocalDateTime.now()));
        Map<Long, Integer> itemMap = new HashMap<>();
        itemMap.put(saveItem.getItemId(), 1);
        basketFacade.addBasket(new AddBasketRequestDto(saveUser.getUserId(), itemMap));
        //when
        BasketResponseDto responseDto = basketFacade.getBasket(new BasketRequestDto(saveUser.getUserId()));
        //then
        assertNotNull(responseDto);
        assertEquals(responseDto.detailList().size(), 1);
        assertEquals(responseDto.detailList().get(0).getItem().getItemId(), saveItem.getItemId());
    }
}