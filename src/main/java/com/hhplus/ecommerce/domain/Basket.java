package com.hhplus.ecommerce.domain;

import com.hhplus.ecommerce.common.constant.BasketStatus;
import com.hhplus.ecommerce.common.constant.OrderStatus;
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

    @OneToOne
    @JoinColumn(name="user_id")
    private User basketUser;

    @Enumerated(EnumType.STRING)
    private BasketStatus basketStatus;

    @Column(name = "CREATE_TIME")
    private LocalDateTime createTime;

    @OneToMany(mappedBy = "basket", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BasketDetail> basketDetails = new ArrayList<>();

    public Basket(User basketUser, LocalDateTime createTime) {
        this.basketUser = basketUser;
        this.basketStatus = BasketStatus.SHOPPING;
        this.createTime = createTime;
    }

    public void addBasketDetail(BasketDetail basketDetail) {
        this.basketDetails.add(basketDetail);
    }

    public void removeBasketDetail(BasketDetail basketDetail) {
        this.basketDetails.remove(basketDetail);
    }

    public int totalPrice() {
        int totalPrice = 0;
        for (BasketDetail basketDetail : basketDetails) {
            totalPrice = basketDetail.getItem().getItemPrice();
        }

        return totalPrice;
    }
}
