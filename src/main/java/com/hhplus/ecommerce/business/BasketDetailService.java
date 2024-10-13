package com.hhplus.ecommerce.business;

import com.hhplus.ecommerce.infrastructure.BasketDetailRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BasketDetailService {

    private BasketDetailRepository basketDetailRepository;


}
