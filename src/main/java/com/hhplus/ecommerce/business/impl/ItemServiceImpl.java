package com.hhplus.ecommerce.business.impl;

import com.hhplus.ecommerce.business.ItemService;
import com.hhplus.ecommerce.infrastructure.ItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
}
