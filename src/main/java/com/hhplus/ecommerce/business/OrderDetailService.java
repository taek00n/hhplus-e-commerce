package com.hhplus.ecommerce.business;

import com.hhplus.ecommerce.common.exception.RestApiException;
import com.hhplus.ecommerce.common.exception.domain.OrderErrorCode;
import com.hhplus.ecommerce.domain.Order;
import com.hhplus.ecommerce.domain.OrderDetail;
import com.hhplus.ecommerce.infrastructure.OrderDetailRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderDetailService {

    private final OrderDetailRepository orderDetailRepository;

    public List<OrderDetail> getOrderDetailByOrder(Order order) {

        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrder(order);

        if (orderDetailList.isEmpty()) {
            throw new RestApiException(OrderErrorCode.NO_DETAIL_BY_ORDER);
        }
        
        return orderDetailList;
    }
}
