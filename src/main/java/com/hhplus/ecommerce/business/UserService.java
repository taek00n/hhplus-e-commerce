package com.hhplus.ecommerce.business;

import com.hhplus.ecommerce.domain.User;
import com.hhplus.ecommerce.infrastructure.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getUserByUserId(long userId) {

        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("없는 사용자입니다."));

        return user;
    }

    public User createUser(User user) {

        return userRepository.save(user);
    }

    public User chargeUserBalance(long userId, int chargeBalance) {

        User user = this.getUserByUserId(userId);
        user.chargeBalance(chargeBalance);

        return user;
    }

    public User useUserBalance(long userId, int useBalance) {

        User user = this.getUserByUserId(userId);
        user.useBalance(useBalance);

        return user;
    }
}
