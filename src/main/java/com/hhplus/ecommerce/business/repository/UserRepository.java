package com.hhplus.ecommerce.business.repository;

import com.hhplus.ecommerce.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
