package com.hhplus.ecommerce.application;

import com.hhplus.ecommerce.common.exception.RestApiException;
import com.hhplus.ecommerce.common.exception.domain.UserErrorCode;
import com.hhplus.ecommerce.domain.User;
import com.hhplus.ecommerce.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getUserByUserId(long userId) {

        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new RestApiException(UserErrorCode.NO_USER_BY_ID));

        return user;
    }

    public User createUser(User user) {

        return userRepository.save(user);
    }

    public User chargeUserBalance(long userId, int chargeBalance) {

        User getLockUser = userRepository.findByUserIdWithLock(userId).orElseThrow(
                () -> new RestApiException(UserErrorCode.NO_USER_BY_ID)
        );
        getLockUser.chargeBalance(chargeBalance);

        return getLockUser;
    }

    public User useUserBalance(long userId, int useBalance) {

        User user = this.getUserByUserId(userId);
        user.useBalance(useBalance);

        return user;
    }
}
