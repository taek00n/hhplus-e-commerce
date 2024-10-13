package com.hhplus.ecommerce.business.impl;

import com.hhplus.ecommerce.business.UserService;
import com.hhplus.ecommerce.infrastructure.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
}
