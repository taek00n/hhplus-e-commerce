package com.hhplus.ecommerce.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "BASKET")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Basket {

    @Id @GeneratedValue
    @Column(name = "BASKET_ID", nullable = false)
    private Long basketId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User basketUser;

    @Column(name = "ORDER_YN")
    private String orderYn;

    @Column(name = "CREATE_TIME")
    private LocalDateTime createTime;

    @OneToMany(mappedBy = "basket", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BasketDetail> basketDetails = new ArrayList<>();

    public Basket(Long basketId, User basketUser, LocalDateTime createTime) {
        this.basketId = basketId;
        this.basketUser = basketUser;
        this.orderYn = "N";
        this.createTime = createTime;
    }

    public void addBasketDetail(BasketDetail basketDetail) {
        this.basketDetails.add(basketDetail);
    }

    public void removeBasketDetail(BasketDetail basketDetail) {
        this.basketDetails.remove(basketDetail);
    }
}
