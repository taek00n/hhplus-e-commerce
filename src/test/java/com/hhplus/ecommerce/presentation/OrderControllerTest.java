package com.hhplus.ecommerce.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hhplus.ecommerce.domain.OrderDetail;
import com.hhplus.ecommerce.presentation.dto.request.order.CreateOrderRequestDto;
import com.hhplus.ecommerce.presentation.dto.request.order.OrderRequestDto;
import com.hhplus.ecommerce.presentation.dto.request.pay.PayRequestDto;
import com.hhplus.ecommerce.presentation.dto.response.order.CreateOrderResponseDto;
import com.hhplus.ecommerce.presentation.dto.response.order.OrderResponseDto;
import com.hhplus.ecommerce.presentation.dto.response.pay.PayResponseDto;
import com.hhplus.ecommerce.presentation.facade.OrderFacade;
import com.hhplus.ecommerce.presentation.facade.PayFacade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderFacade orderFacade;

    @MockBean
    private PayFacade payFacade;

    @Autowired
    private ObjectMapper objectMapper;

    private Long userId = 1L;
    private Long itemId = 2L;
    private Long orderId = 3L;
    private Map<Long, Integer> itemMap = new HashMap<>();

    @Test
    @DisplayName("사용자의 주문을 생성한 후 주문번호,사용자아이디,총금액,총갯수를 반환받는다.")
    void createOrder() throws Exception {
        //given
        itemMap.put(itemId, 1);
        CreateOrderRequestDto requestDto = new CreateOrderRequestDto(userId, itemMap);
        when(orderFacade.createOrder(requestDto)).thenReturn(new CreateOrderResponseDto(orderId, userId, 10000, 1));
        //when
        ResultActions resultActions = mockMvc.perform(
                post("/orders/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto))
        );
        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value(3))
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.totalPrice").value(10000))
                .andExpect(jsonPath("$.totalAmount").value(1))
                .andDo(print());
    }

    @Test
    @DisplayName("주문생성 후 생성된 주문의 번호를 반환 받는다.")
    void getOrder() throws Exception {
        //given
        OrderRequestDto requestDto = new OrderRequestDto(userId);
        List<OrderDetail> orderDetailList = new ArrayList<>();
        when(orderFacade.getUserOrder(requestDto)).thenReturn(new OrderResponseDto(orderId, orderDetailList, 10000, 1)).thenReturn(new OrderResponseDto(orderId, orderDetailList, 10000, 2));
        //when
        ResultActions resultActions = mockMvc.perform(
                post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto))
        );
        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value(3))
                .andDo(print());
    }

    @Test
    @DisplayName("주문번호로 결제 시도 후 성공과 msg를 반환받는다.")
    void pay() throws Exception {
        //given
        PayRequestDto requestDto = new PayRequestDto(orderId);
        when(payFacade.receiveOrderToPay(requestDto)).thenReturn(new PayResponseDto(true, "결제완료하였습니다."));
        //when
        ResultActions resultActions = mockMvc.perform(
                post("/orders/pay")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto))
        );
        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.msg").value("결제완료하였습니다."))
                .andDo(print());
    }
}