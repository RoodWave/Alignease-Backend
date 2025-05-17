package com.alignease.v1.controller;

import com.alignease.v1.dto.request.AuthRequest;
import com.alignease.v1.dto.response.AuthResponse;
import com.alignease.v1.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest authRequest) {
        return authService.login(authRequest);
    }

    @PostMapping("/signup")
    public AuthResponse signUp(@RequestBody AuthRequest authRequest) {
        return authService.signUp(authRequest);
    }
}
