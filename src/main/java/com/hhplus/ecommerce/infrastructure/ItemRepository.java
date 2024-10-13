package com.hhplus.ecommerce.infrastructure;

import com.hhplus.ecommerce.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
