package com.springboot.security.service;

import com.springboot.security.domain.User;
import com.springboot.security.domain.dto.UserJoinRequest;
import com.springboot.security.domain.dto.UserJoinResponse;
import com.springboot.security.exception.AppException;
import com.springboot.security.exception.ErrorCode;
import com.springboot.security.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserJoinResponse join(UserJoinRequest userJoinRequest) {
        userRepository.findByUsername(userJoinRequest.getUsername())
                .ifPresent(user->{
                    throw new AppException(ErrorCode.DUPLICATED_USER_NAME, String.format("%s는 존재하는 이름입니다.", userJoinRequest.getUsername()));
                });
        User user=userRepository.save(userJoinRequest.toEntity());

        return UserJoinResponse.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .message("회원가입에 성공했습니다.")
                .build();
    }
}
