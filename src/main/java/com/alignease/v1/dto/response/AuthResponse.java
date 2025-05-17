package com.alignease.v1.dto.response;

import com.alignease.v1.entity.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthResponse extends Response{
    private User user;
}
