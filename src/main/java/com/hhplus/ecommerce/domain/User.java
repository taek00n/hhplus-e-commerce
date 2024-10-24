package com.hhplus.ecommerce.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "USERS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User {

    @Id @GeneratedValue
    @Column(name = "USER_ID", nullable = false)
    private Long userId;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "BALANCE")
    private int balance;

    @Column(name = "JOIN_DATE")
    private LocalDateTime joinDate;

    @OneToMany(mappedBy = "orderUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    public User(String userName, int balance, LocalDateTime joinDate) {
        this.userName = userName;
        this.balance = balance;
        this.joinDate = joinDate;
    }

    public void chargeBalance(int chargeBalance) {
        this.balance += chargeBalance;
    }

    public void useBalance(int useBalance) {
        this.balance -= useBalance;
    }
}
