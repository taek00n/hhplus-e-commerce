package com.hhplus.ecommerce.domain;

import com.hhplus.ecommerce.common.exception.RestApiException;
import com.hhplus.ecommerce.common.exception.domain.UserErrorCode;
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

    @Version
    private Integer version;

    public User(String userName, int balance, LocalDateTime joinDate) {
        this.userName = userName;
        this.balance = balance;
        this.joinDate = joinDate;
    }

    public void chargeBalance(int chargeBalance) {
        this.balance += chargeBalance;
    }

    public void useBalance(int useBalance) {
        int remainBalance = this.balance - useBalance;
        if (remainBalance < 0) {
            throw new RestApiException(UserErrorCode.NOT_ENOUGH_BALANCE);
        }
        this.balance = remainBalance;
    }
}
