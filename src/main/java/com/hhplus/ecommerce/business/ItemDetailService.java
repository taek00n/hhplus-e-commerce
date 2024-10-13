package com.hhplus.ecommerce.business;

import com.hhplus.ecommerce.domain.ItemDetail;
import com.hhplus.ecommerce.infrastructure.ItemDetailRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ItemDetailService {

    private final ItemDetailRepository itemDetailRepository;

    public ItemDetail save(ItemDetail itemDetail) {

        return itemDetailRepository.save(itemDetail);
    }

    public ItemDetail findById(Long itemDetailId) {

        return itemDetailRepository.findById(itemDetailId).orElse(null);
    }
}
