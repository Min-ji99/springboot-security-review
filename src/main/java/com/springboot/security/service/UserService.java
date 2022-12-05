package com.springboot.security.service;

import com.springboot.security.domain.User;
import com.springboot.security.domain.dto.UserJoinRequest;
import com.springboot.security.domain.dto.UserJoinResponse;
import com.springboot.security.domain.dto.UserLoginRequest;
import com.springboot.security.domain.dto.UserLoginResponse;
import com.springboot.security.exception.AppException;
import com.springboot.security.exception.ErrorCode;
import com.springboot.security.repository.UserRepository;
import com.springboot.security.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Value("${jwt.token.secret}")
    private String secretKey;

    private long expireTimeMs = 1000 * 60 * 60;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public UserJoinResponse join(UserJoinRequest userJoinRequest) {
        userRepository.findByUsername(userJoinRequest.getUsername())
                .ifPresent(user->{
                    throw new AppException(ErrorCode.DUPLICATED_USER_NAME, String.format("%s는 존재하는 이름입니다.", userJoinRequest.getUsername()));
                });
        User user=userRepository.save(userJoinRequest.toEntity(encoder.encode(userJoinRequest.getPassword())));

        return UserJoinResponse.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .message("회원가입에 성공했습니다.")
                .build();
    }

    public String login(UserLoginRequest userLoginRequest) {
        User user=userRepository.findByUsername(userLoginRequest.getUsername())
                .orElseThrow(()->new AppException(ErrorCode.NOTFOUND_USER_NAME, String.format("존재하지 않는 username입니다.")));
        if(!encoder.matches(userLoginRequest.getPassword(), user.getPassword())){
            throw new AppException(ErrorCode.INVALID_PASSWORD, String.format("username 또는 password가 틀렸습니다."));
        }
        return JwtTokenUtil.createToken(userLoginRequest.getUsername(), secretKey, expireTimeMs);
    }
    public User getUserByUserName(String username){
        return userRepository.findByUsername(username)
                .orElseThrow(()->new AppException(ErrorCode.NOTFOUND_USER_NAME, ""));
    }
}
