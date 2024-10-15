package com.hhplus.ecommerce.infrastructure;

import com.hhplus.ecommerce.domain.ItemDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemDetailRepository extends JpaRepository<ItemDetail, Long> {
}
