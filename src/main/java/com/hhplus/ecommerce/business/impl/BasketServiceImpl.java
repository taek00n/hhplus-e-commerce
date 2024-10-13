package com.hhplus.ecommerce.business.impl;

import com.hhplus.ecommerce.business.BasketService;
import com.hhplus.ecommerce.infrastructure.BasketRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BasketServiceImpl implements BasketService {

    private final BasketRepository basketRepository;

}
