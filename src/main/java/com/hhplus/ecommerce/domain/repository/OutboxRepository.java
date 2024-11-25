package com.hhplus.ecommerce.domain.repository;

import com.hhplus.ecommerce.domain.Outbox;

import java.util.List;

public interface OutboxRepository {

    Outbox save(Outbox outbox);
    List<Outbox> getPendingOutbox();
    Outbox getOutboxByValueId(Long valueId);
}
