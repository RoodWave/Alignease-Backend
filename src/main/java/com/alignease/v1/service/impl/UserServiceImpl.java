package com.alignease.v1.service.impl;

import com.alignease.v1.repository.UserRepository;
import com.alignease.v1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
}
