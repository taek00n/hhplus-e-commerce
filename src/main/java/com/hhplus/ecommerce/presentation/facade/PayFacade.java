package com.hhplus.ecommerce.presentation.facade;

import com.hhplus.ecommerce.business.ItemService;
import com.hhplus.ecommerce.business.OrderService;
import com.hhplus.ecommerce.business.UserService;
import com.hhplus.ecommerce.presentation.dto.request.pay.PayRequestDto;
import com.hhplus.ecommerce.presentation.dto.response.pay.PayResponseDto;
import com.hhplus.ecommerce.domain.Item;
import com.hhplus.ecommerce.domain.Order;
import com.hhplus.ecommerce.domain.OrderDetail;
import com.hhplus.ecommerce.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PayFacade {

    private final OrderService orderService;
    private final UserService userService;
    private final ItemService itemService;

    public PayResponseDto receiveOrderToPay(PayRequestDto requestDto) {

        Order order = orderService.getOrder(requestDto.orderId());

        User orderUser = userService.getUser(order.getOrderUser().getUserId());
        for (OrderDetail orderDetail : order.getOrderDetails()) {
            Item orderItem = itemService.getItem(orderDetail.getItem().getItemId());

            if (orderItem.getItemStock() < orderDetail.getAmount()) {
                return new PayResponseDto(false, orderItem.getItemName() + "의 재고부족 (현재 " + orderItem.getItemStock() + "개 남아있습니다.)");
            }
        }

        if (orderUser.getBalance() > order.getTotalPrice()) {
            int minusBalance = order.getTotalPrice() - orderUser.getBalance();
            return new PayResponseDto(false, minusBalance + "만큼 잔액을 충전해주세요");
        }

        return new PayResponseDto(true, "결제 성공하였습니다.");
    }
}
