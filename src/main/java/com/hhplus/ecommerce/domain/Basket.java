package com.hhplus.ecommerce.domain;

import com.hhplus.ecommerce.common.constant.BasketStatus;
import com.hhplus.ecommerce.common.constant.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
    name = "BASKET",
    indexes = {
        @Index(name = "idx_basket_user_id", columnList = "user_id")
    }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Basket {

    @Id @GeneratedValue
    @Column(name = "BASKET_ID", nullable = false)
    private Long basketId;

    @OneToOne
    @JoinColumn(name="USER_ID")
    private User basketUser;

    @Enumerated(EnumType.STRING)
    private BasketStatus basketStatus;

    @Column(name = "CREATE_TIME")
    private LocalDateTime createTime;

    public Basket(User basketUser) {
        this.basketUser = basketUser;
        this.basketStatus = BasketStatus.SHOPPING;
        this.createTime = LocalDateTime.now();
    }
}
