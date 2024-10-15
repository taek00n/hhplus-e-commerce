package com.hhplus.ecommerce.business;

import com.hhplus.ecommerce.infrastructure.ItemDetailRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ItemDetailService {

    private final ItemDetailRepository itemDetailRepository;
}
