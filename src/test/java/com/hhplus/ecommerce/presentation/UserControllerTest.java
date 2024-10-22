package com.hhplus.ecommerce.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hhplus.ecommerce.presentation.dto.request.user.UserBalanceRequestDto;
import com.hhplus.ecommerce.presentation.dto.request.user.UserChargeRequestDto;
import com.hhplus.ecommerce.presentation.dto.response.user.UserBalanceResponseDto;
import com.hhplus.ecommerce.presentation.dto.response.user.UserChargeResponseDto;
import com.hhplus.ecommerce.presentation.facade.UserFacade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserFacade userFacade;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("40000원 있는 사용자의 보유금 조회하면 40000원이 나온다")
    void getBalance() throws Exception {
        //given
        UserBalanceRequestDto requestDto = new UserBalanceRequestDto(1L);
        when(userFacade.getUserBalance(requestDto)).thenReturn(new UserBalanceResponseDto(1L, 40000));
        //when
        ResultActions resultActions = mockMvc.perform(
                post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto))
        );
        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(40000));
    }

    @Test
    @DisplayName("30000원을 충전한 후에 충전금액, 총금액을 반환받는다.")
    void chargeBalance() throws Exception {
        //given
        UserChargeRequestDto requestDto = new UserChargeRequestDto(1L, 30000);
        when(userFacade.chargeUserBalance(requestDto)).thenReturn(new UserChargeResponseDto(1L, 30000, 30000));
        //when
        ResultActions resultActions = mockMvc.perform(
                post("/user/charge")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto))
        );
        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(30000))
                .andExpect(jsonPath("$.totalBalance").value(30000));
    }
}