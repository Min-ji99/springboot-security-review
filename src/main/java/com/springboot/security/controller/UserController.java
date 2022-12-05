package com.springboot.security.controller;

import com.springboot.security.domain.dto.UserJoinRequest;
import com.springboot.security.domain.dto.UserJoinResponse;
import com.springboot.security.domain.dto.UserLoginRequest;
import com.springboot.security.domain.dto.UserLoginResponse;
import com.springboot.security.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/join")
    public ResponseEntity<UserJoinResponse> join(@RequestBody UserJoinRequest userJoinRequest){
        UserJoinResponse userJoinResponse=userService.join(userJoinRequest);
        return ResponseEntity.ok().body(userJoinResponse);
    }
    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> login(@RequestBody UserLoginRequest userLoginRequest){
        String token=userService.login(userLoginRequest);
        return ResponseEntity.ok().body(new UserLoginResponse(token));
    }
}
