package com.alignease.v1.service;

import com.alignease.v1.dto.request.AuthRequest;
import com.alignease.v1.dto.response.AuthResponse;

public interface AuthService {
    AuthResponse login(AuthRequest authRequest);
    AuthResponse signUp(AuthRequest authRequest);
}
