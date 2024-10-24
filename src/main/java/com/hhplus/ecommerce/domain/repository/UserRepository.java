package com.hhplus.ecommerce.domain.repository;

import com.hhplus.ecommerce.domain.User;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository {

    User save(User user);
    Optional<User> findByUserId(Long userId);
    Optional<User> findByUserIdWithLock(long userId);
}
