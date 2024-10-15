package com.hhplus.ecommerce.business;

import com.hhplus.ecommerce.domain.User;
import com.hhplus.ecommerce.infrastructure.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getUser(long userId) {

        User user = userRepository.getUser(userId)
                .orElseThrow(() -> new IllegalArgumentException("없는 사용자입니다."));

        return user;
    }

    public User createUser(User user) {

        return userRepository.createUser(user);
    }

    public User chargeUserBalance(long userId, int chargeBalance) {

        User user = this.getUser(userId);
        user.chargeBalance(chargeBalance);

        return user;
    }

    public User useUserBalance(long userId, int useBalance) {

        User user = this.getUser(userId);
        user.useBalance(useBalance);

        return user;
    }
}
