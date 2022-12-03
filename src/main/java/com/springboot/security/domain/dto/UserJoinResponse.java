package com.springboot.security.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Generated;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class UserJoinResponse {
    private String username;
    private String email;
    private String message;
}
