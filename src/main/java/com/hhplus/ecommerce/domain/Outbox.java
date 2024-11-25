package com.hhplus.ecommerce.domain;

import com.hhplus.ecommerce.common.constant.OutboxStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "outbox")
public class Outbox {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String topic;

    @Column(nullable = false)
    private Long valueId;

    @Enumerated(EnumType.STRING)
    private OutboxStatus status;

    @Column
    private LocalDateTime createDate;

    public Outbox(String topic, Long valueId) {
        this.topic = topic;
        this.valueId = valueId;
        this.status = OutboxStatus.CREATE;
        this.createDate = LocalDateTime.now();
    }

    public void setStatus(OutboxStatus status) {

        this.status = status;
    }
}
