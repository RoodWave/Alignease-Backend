package com.alignease.v1.service.impl;

import com.alignease.v1.dto.request.AuthRequest;
import com.alignease.v1.dto.response.AuthResponse;
import com.alignease.v1.entity.User;
import com.alignease.v1.exception.AlignEaseValidationsException;
import com.alignease.v1.repository.UserRepository;
import com.alignease.v1.service.AuthService;
import com.alignease.v1.utils.Constant;
import com.alignease.v1.utils.Messages;
import com.alignease.v1.utils.RequestStatus;
import com.alignease.v1.utils.ResponseCodes;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    Messages messages;

    @Autowired
    ModelMapper modelMapper;

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Override
    public AuthResponse login(AuthRequest authRequest) {
        logger.info("Log In Starts");

        AuthResponse authResponse = new AuthResponse();

        try {
            Optional<User> activeUserByEmail = userRepository.findActiveUserByEmail(authRequest.getEmail());
            if (activeUserByEmail.isEmpty()) {
                throw new AlignEaseValidationsException(ResponseCodes.BAD_REQUEST_CODE, messages.getMessageForResponseCode(ResponseCodes.USER_NOT_FOUND, null));
            }

            User user = activeUserByEmail.get();

            if (!bCryptPasswordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
                throw new AlignEaseValidationsException(ResponseCodes.BAD_REQUEST_CODE,
                        messages.getMessageForResponseCode(ResponseCodes.PASSWORD_MISMATCH, null));
            }

            logger.info("Log In Success");
            authResponse.setUser(user);
            authResponse.setStatus(RequestStatus.SUCCESS.getStatus());
            authResponse.setResponseCode(ResponseCodes.SUCCESS);
            authResponse.setMessage(messages.getMessageForResponseCode(ResponseCodes.USER_SIGN_IN_SUCCESS, null));
        } catch (Exception e) {
            throw new AlignEaseValidationsException(ResponseCodes.BAD_REQUEST_CODE, messages.getMessageForResponseCode(ResponseCodes.USER_SIGN_IN_FAILURE, null));
        }
        logger.info("Log In Ends");
        return authResponse;
    }

    @Override
    public AuthResponse signUp(AuthRequest authRequest) {
        logger.info("Sign Up Starts");

        AuthResponse authResponse = new AuthResponse();

        try {
            Optional<User> activeUserByEmail = userRepository.findActiveUserByEmail(authRequest.getEmail());
            if (activeUserByEmail.isPresent()) {
                throw new AlignEaseValidationsException(ResponseCodes.BAD_REQUEST_CODE, messages.getMessageForResponseCode(ResponseCodes.EMAIL_ALREADY_TAKEN, null));
            }

            User user = modelMapper.map(authRequest, User.class);
            user.setIsDeleted(Constant.DB_FALSE);
            user.setPassword(bCryptPasswordEncoder.encode(authRequest.getPassword()));

            User save = userRepository.save(user);
            logger.info("Sign Up Success");
            authResponse.setUser(save);
            authResponse.setStatus(RequestStatus.SUCCESS.getStatus());
            authResponse.setResponseCode(ResponseCodes.SUCCESS);
            authResponse.setMessage(messages.getMessageForResponseCode(ResponseCodes.USER_SIGNUP_SUCCESS, null));
        } catch (Exception e) {
            throw new AlignEaseValidationsException(ResponseCodes.BAD_REQUEST_CODE, messages.getMessageForResponseCode(ResponseCodes.USER_SIGNUP_FAILURE, null));
        }
        logger.info("Sign Up Ends");
        return authResponse;
    }
}
