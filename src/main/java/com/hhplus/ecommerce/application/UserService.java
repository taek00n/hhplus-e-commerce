package com.hhplus.ecommerce.application;

import com.hhplus.ecommerce.common.exception.RestApiException;
import com.hhplus.ecommerce.common.exception.domain.UserErrorCode;
import com.hhplus.ecommerce.domain.User;
import com.hhplus.ecommerce.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
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

    @Transactional
    public User chargeUserBalance(long userId, int chargeBalance) {

        // 비관적락
//        User getUser = userRepository.findByUserIdWithLock(userId).orElseThrow(
//                () -> new RestApiException(UserErrorCode.NO_USER_BY_ID)
//        );

        // 낙관적락
        User getUser = userRepository.findByUserId(userId).orElseThrow(
                () -> new RestApiException(UserErrorCode.NO_USER_BY_ID)
        );

        getUser.chargeBalance(chargeBalance);

        return getUser;
    }

    @Transactional
    public User useUserBalance(long userId, int useBalance) {

        // 비관적락
//        User getUser = userRepository.findByUserIdWithLock(userId).orElseThrow(
//                () -> new RestApiException(UserErrorCode.NO_USER_BY_ID)
//        );

        // 낙관적락
        User getUser = userRepository.findByUserId(userId).orElseThrow(
                () -> new RestApiException(UserErrorCode.NO_USER_BY_ID)
        );

        getUser.useBalance(useBalance);

        return getUser;
    }
}
