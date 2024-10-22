package com.hhplus.ecommerce.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hhplus.ecommerce.domain.Basket;
import com.hhplus.ecommerce.domain.BasketDetail;
import com.hhplus.ecommerce.domain.Item;
import com.hhplus.ecommerce.domain.User;
import com.hhplus.ecommerce.presentation.dto.request.basket.AddBasketRequestDto;
import com.hhplus.ecommerce.presentation.dto.request.basket.BasketRequestDto;
import com.hhplus.ecommerce.presentation.dto.request.basket.DeleteBasketDetailRequestDto;
import com.hhplus.ecommerce.presentation.dto.response.basket.AddBasketResponseDto;
import com.hhplus.ecommerce.presentation.dto.response.basket.BasketResponseDto;
import com.hhplus.ecommerce.presentation.dto.response.basket.DeleteBasketDetailResponseDto;
import com.hhplus.ecommerce.presentation.facade.BasketFacade;
import org.junit.jupiter.api.BeforeEach;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BasketController.class)
class BasketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BasketFacade basketFacade;

    @Autowired
    ObjectMapper objectMapper;

    private User saveUser;

    @BeforeEach
    void setUp() {
        saveUser = new User("김태현", 0, LocalDateTime.now());
    }

    @Test
    @DisplayName("장바구니의 상품 추가 후 바구니의 아이디를 받는 API 테스트")
    void addBasket() throws Exception {
        //given
        Map<Long, Integer> itemMap = new HashMap<>();
        itemMap.put(1L, 1);
        AddBasketRequestDto requestDto = new AddBasketRequestDto(2L, itemMap);
        when(basketFacade.addBasket(requestDto)).thenReturn(new AddBasketResponseDto(23L));
        //when
        ResultActions resultActions = mockMvc.perform(
                post("/basket/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto))
        );
        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.basketId").value(23))
                .andDo(print());
    }

    @Test
    @DisplayName("장바구니의 상품을 삭제하면 상품의 아이디와 상품명을 받는 API 테스트")
    void deleteBasketDetail() throws Exception {
        //given
        DeleteBasketDetailRequestDto requestDto = new DeleteBasketDetailRequestDto(1L, 1L);
        when(basketFacade.deleteBasketDetailByItem(requestDto)).thenReturn(new DeleteBasketDetailResponseDto(1L, "청바지"));
        //when
        ResultActions resultActions = mockMvc.perform(
                delete("/basket/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto))
        );
        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.itemId").value(1))
                .andExpect(jsonPath("$.itemName").value("청바지"))
                .andDo(print());
    }

    @Test
    @DisplayName("장바구니의 상세 삼품 조회 API 테스트")
    void getBasket() throws Exception {
        //given
        BasketRequestDto requestDto = new BasketRequestDto(1L);
        Item item = new Item("청바지", 30000, 40, LocalDateTime.now());
        User user = new User("김태현", 0, LocalDateTime.now());
        new Item("청바지", 30000, 40, LocalDateTime.now());
        Basket basket = new Basket(user, LocalDateTime.now());
        BasketDetail basketDetail = new BasketDetail(basket, item, 1);
        List<BasketDetail> basketDetailList = new ArrayList<>();
        basketDetailList.add(basketDetail);
        when(basketFacade.getBasket(requestDto)).thenReturn(new BasketResponseDto(2L, basketDetailList));
        //when
        ResultActions resultActions = mockMvc.perform(
                post("/basket")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto))
        );
        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.basketId").value(2))
                .andDo(print());
    }
}