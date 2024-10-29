package com.hhplus.ecommerce.presentation;

import com.hhplus.ecommerce.domain.Item;
import com.hhplus.ecommerce.presentation.dto.response.item.ItemResponseDto;
import com.hhplus.ecommerce.presentation.dto.response.item.ItemsResponseDto;
import com.hhplus.ecommerce.presentation.dto.response.item.PopularItemsResponseDto;
import com.hhplus.ecommerce.presentation.facade.ItemFacade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ItemController.class)
class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemFacade itemFacade;

    @Test
    @DisplayName("전체 상품 조회 API 테스트")
    void getItems() throws Exception {
        // given
        Item item1 = new Item("후드티1", 100000, 1, LocalDateTime.now());
        Item item2 = new Item("후드티2", 200000, 2, LocalDateTime.now());
        Item item3 = new Item("후드티3", 300000, 3, LocalDateTime.now());
        List<Item> itemList = new ArrayList<>();
        itemList.add(item1);
        itemList.add(item2);
        itemList.add(item3);
        when(itemFacade.getItemAll()).thenReturn(new ItemsResponseDto(itemList));
        //when
        ResultActions resultActions = mockMvc.perform(
                get("/items")
                        .contentType(MediaType.APPLICATION_JSON)
        );
        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items[0].itemName").value(item1.getItemName()))
                .andDo(print());
    }

    @Test
    @DisplayName("특정 상품 조회 API 테스트")
    void getItem() throws Exception {
        // given
        Item item = new Item("후드티", 100000, 3, LocalDateTime.now());
        when(itemFacade.getItemByItemId(1L)).thenReturn(new ItemResponseDto(item.getItemId(), item.getItemName(), item.getItemPrice(), item.getItemStock()));
        //when
        ResultActions resultActions = mockMvc.perform(
                get("/items/{itemId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.itemName").value(item.getItemName()))
                .andDo(print());
    }

    @Test
    @DisplayName("인기을 조회하면 최근 3일간 수량 top이 반환된다.")
    void getPopularItem() throws Exception {
        // given
        Item item1 = new Item("후드티1", 100000, 3, LocalDateTime.now());
        Item item2 = new Item("후드티2", 200000, 3, LocalDateTime.now());
        Item item3 = new Item("후드티3", 300000, 3, LocalDateTime.now());
        Item item4 = new Item("후드티4", 400000, 3, LocalDateTime.now());
        Item item5 = new Item("후드티5", 500000, 3, LocalDateTime.now());
        List<Item> itemList = new ArrayList<>();
        itemList.add(item1);
        itemList.add(item2);
        itemList.add(item3);
        itemList.add(item4);
        itemList.add(item5);
        when(itemFacade.getPopularItem()).thenReturn(new PopularItemsResponseDto(itemList));
        //when
        ResultActions resultActions = mockMvc.perform(
                get("/items/popular")
                        .contentType(MediaType.APPLICATION_JSON)
        );
        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items[0].itemName").value(item1.getItemName()))
                .andDo(print());
    }

}