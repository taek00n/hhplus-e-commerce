package com.hhplus.ecommerce.business;

import com.hhplus.ecommerce.domain.BasketDetail;
import com.hhplus.ecommerce.infrastructure.BasketDetailRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BasketDetailService {

    private BasketDetailRepository basketDetailRepository;


    public BasketDetail save(BasketDetail basketDetail) {

        return basketDetailRepository.save(basketDetail);
    }

    public void delete(Long basketDetailId) {

        BasketDetail basketDetail = basketDetailRepository.findById(basketDetailId)
                .orElseThrow(() -> new IllegalArgumentException("삭제 할려는 상품은 장바구니에 없습니다."));
        basketDetailRepository.delete(basketDetail);
    }

    public BasketDetail findById(Long basketDetailId) {

        BasketDetail basketDetail = basketDetailRepository.findById(basketDetailId)
                .orElseThrow(() -> new IllegalArgumentException("조회 할려는 상품은 장바구니에 없습니다."));

        return basketDetail;
    }
}
