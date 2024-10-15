package com.hhplus.ecommerce.presentation;

import com.hhplus.ecommerce.business.facade.OrderFacade;
import com.hhplus.ecommerce.business.facade.PayFacade;
import com.hhplus.ecommerce.presentation.dto.request.order.CreateOrderRequestDto;
import com.hhplus.ecommerce.presentation.dto.request.pay.PayRequestDto;
import com.hhplus.ecommerce.presentation.dto.response.order.CreateOrderResponseDto;
import com.hhplus.ecommerce.presentation.dto.response.pay.PayResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/orders")
@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderFacade orderFacade;
    private final PayFacade payFacade;

    @PostMapping
    public CreateOrderResponseDto createOrder(@RequestBody CreateOrderRequestDto createOrderRequestDto) {

        return orderFacade.createOrder(createOrderRequestDto);
    }

    @PostMapping("/pay")
    public PayResponseDto pay(@RequestBody PayRequestDto payRequestDto) {

        return payFacade.receiveOrderToPay(payRequestDto);
    }
}
