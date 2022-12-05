package com.springboot.security.domain.dto;

import com.springboot.security.domain.User;
import com.springboot.security.domain.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class UserJoinRequest {
    private String username;
    private String password;
    private String email;

    public User toEntity(String password) {
        return User.builder()
                .username(username)
                .password(password)
                .email(email)
                .userRole(UserRole.USER)
                .build();
    }
}
