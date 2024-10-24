package com.hhplus.ecommerce.infrastructure;

import com.hhplus.ecommerce.domain.User;
import com.hhplus.ecommerce.domain.repository.UserRepository;
import com.hhplus.ecommerce.infrastructure.jpa.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public User save(User user) {
        User saveUser = userJpaRepository.save(user);

        return saveUser;
    }

    @Override
    public Optional<User> findByUserId(Long userId) {

        Optional<User> getUser = userJpaRepository.findById(userId);

        return getUser;
    }

    @Override
    public Optional<User> findByUserIdWithLock(long userId) {

        Optional<User> getLockUser = userJpaRepository.findByUserIdWithLock(userId);

        return getLockUser;
    }
}
