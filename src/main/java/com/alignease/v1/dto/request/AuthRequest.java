package com.alignease.v1.dto.request;

import com.alignease.v1.entity.UserRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequest {
    private String email;
    private String password;
    private String userName;
    private UserRole userRole;
}
