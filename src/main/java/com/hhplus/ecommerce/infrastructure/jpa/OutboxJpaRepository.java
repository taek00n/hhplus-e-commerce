package com.hhplus.ecommerce.infrastructure.jpa;

import com.hhplus.ecommerce.common.constant.OutboxStatus;
import com.hhplus.ecommerce.domain.Outbox;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OutboxJpaRepository extends JpaRepository<Outbox, Long> {

    List<Outbox> findByStatus(OutboxStatus status);
    Outbox findByValueId(Long valueId);
}
