package com.hhplus.ecommerce.infrastructure;

import com.hhplus.ecommerce.common.constant.OutboxStatus;
import com.hhplus.ecommerce.domain.Outbox;
import com.hhplus.ecommerce.domain.repository.OutboxRepository;
import com.hhplus.ecommerce.infrastructure.jpa.OutboxJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Repository
public class OutboxRepositoryImpl implements OutboxRepository {

    private final OutboxJpaRepository outboxJpaRepository;

    @Override
    public Outbox save(Outbox outbox) {
        Outbox saveOutbox = outboxJpaRepository.save(outbox);

        return saveOutbox;
    }

    @Override
    public List<Outbox> getPendingOutbox() {

        List<Outbox> pendingOutboxList = outboxJpaRepository.findByStatus(OutboxStatus.PENDING);

        return pendingOutboxList;
    }

    @Override
    public Outbox getOutboxByValueId(Long valueId) {

        Outbox outbox = outboxJpaRepository.findByValueId(valueId);

        return outbox;
    }
}
