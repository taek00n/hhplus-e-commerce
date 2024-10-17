package com.hhplus.ecommerce.infrastructure;

import com.hhplus.ecommerce.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    User save(User user);
    Optional<User> findByUserId(long userId);
}
