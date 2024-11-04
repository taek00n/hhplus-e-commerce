package com.hhplus.ecommerce.presentation.facade;

import com.hhplus.ecommerce.application.ItemService;
import com.hhplus.ecommerce.application.OrderDetailService;
import com.hhplus.ecommerce.application.OrderService;
import com.hhplus.ecommerce.application.UserService;
import com.hhplus.ecommerce.common.constant.OrderStatus;
import com.hhplus.ecommerce.presentation.dto.request.pay.PayRequestDto;
import com.hhplus.ecommerce.presentation.dto.response.pay.PayResponseDto;
import com.hhplus.ecommerce.domain.Item;
import com.hhplus.ecommerce.domain.Order;
import com.hhplus.ecommerce.domain.OrderDetail;
import com.hhplus.ecommerce.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PayFacade {

    private final OrderService orderService;
    private final OrderDetailService orderDetailService;
    private final UserService userService;

    public PayResponseDto receiveOrderToPay(PayRequestDto requestDto) {

        Order order = orderService.getOrderByOrderId(requestDto.orderId());
        List<OrderDetail> orderDetailList = orderDetailService.getOrderDetailByOrder(order);
        int totalPrice = orderDetailList.stream().mapToInt(OrderDetail::getOrderPrice).sum();

        User orderUser = userService.getUserByUserId(order.getOrderUser().getUserId());
        userService.useUserBalance(orderUser.getUserId(), totalPrice);
        order.changeOrderStatus(OrderStatus.PAY);
        return new PayResponseDto(true, "결제 성공하였습니다.");
    }
}
