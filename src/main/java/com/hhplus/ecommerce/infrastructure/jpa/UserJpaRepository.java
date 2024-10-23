package com.hhplus.ecommerce.infrastructure.jpa;

import com.hhplus.ecommerce.domain.User;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<User, Long> {

    User save(User user);
    Optional<User> findByUserId(long userId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "select u from User u where u.userId = :userId")
    Optional<User> findByUserIdWithLock(@Param("userId") long userId);
}
