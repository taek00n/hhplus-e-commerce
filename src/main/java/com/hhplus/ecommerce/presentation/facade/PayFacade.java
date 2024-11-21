package com.hhplus.ecommerce.presentation.facade;

import com.hhplus.ecommerce.application.ItemService;
import com.hhplus.ecommerce.application.OrderDetailService;
import com.hhplus.ecommerce.application.OrderService;
import com.hhplus.ecommerce.application.UserService;
import com.hhplus.ecommerce.common.constant.OrderStatus;
import com.hhplus.ecommerce.infrastructure.event.PayOrderEvent;
import com.hhplus.ecommerce.presentation.dto.request.pay.PayRequestDto;
import com.hhplus.ecommerce.presentation.dto.response.pay.PayResponseDto;
import com.hhplus.ecommerce.domain.Item;
import com.hhplus.ecommerce.domain.Order;
import com.hhplus.ecommerce.domain.OrderDetail;
import com.hhplus.ecommerce.domain.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PayFacade {

    private final OrderService orderService;
    private final OrderDetailService orderDetailService;
    private final UserService userService;

    private final ApplicationEventPublisher publisher;

    /**
     * 주문의 결제를 진행
     * 포인트차감 & 주문 상태 변경 진행
     * */
    @Transactional
    public PayResponseDto receiveOrderToPay(PayRequestDto requestDto) {

        Order order = orderService.getOrderByOrderId(requestDto.orderId());
        List<OrderDetail> orderDetailList = orderDetailService.getOrderDetailByOrder(order);
        int totalPrice = orderDetailList.stream().mapToInt(OrderDetail::getOrderPrice).sum();

        User orderUser = userService.getUserByUserId(order.getOrderUser().getUserId());
        userService.useUserBalance(orderUser.getUserId(), totalPrice); // 포인트 차감
        order.changeOrderStatus(OrderStatus.PAY); // 주문 상태 변경

        publisher.publishEvent(new PayOrderEvent(order.getOrderId()));

        return new PayResponseDto(true, "결제 성공하였습니다.");
    }
}
