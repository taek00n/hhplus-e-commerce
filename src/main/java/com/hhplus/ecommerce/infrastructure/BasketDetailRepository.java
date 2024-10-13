package com.hhplus.ecommerce.infrastructure;

import com.hhplus.ecommerce.domain.BasketDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasketDetailRepository extends JpaRepository<BasketDetail, Long> {
}
