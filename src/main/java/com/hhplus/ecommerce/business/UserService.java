package com.hhplus.ecommerce.business;

import com.hhplus.ecommerce.domain.User;
import com.hhplus.ecommerce.infrastructure.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findUserById(long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("없는 사용자입니다."));

        return user;
    }

    public User save(User user) {

        return userRepository.save(user);
    }

    public User chargeUserBalance(long userId, int chargeBalance) {

        User user = this.findUserById(userId);
        user.chargeBalance(chargeBalance);

        return user;
    }

    public User useUserBalance(long userId, int useBalance) {

        User user = this.findUserById(userId);
        user.useBalance(useBalance);

        return user;
    }
}
