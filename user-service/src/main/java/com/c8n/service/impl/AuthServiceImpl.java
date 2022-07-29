package com.c8n.service.impl;

import com.c8n.constants.DhUserStatus;
import com.c8n.exception.InvalidCredentials;
import com.c8n.model.request.SaveUserDto;
import com.c8n.service.AuthService;
import com.c8n.service.DhUserService;
import com.c8n.util.JwtTokenManager;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final DhUserService userService;
    private final JwtTokenManager jwtTokenManager;

    public AuthServiceImpl(DhUserService userService, JwtTokenManager jwtTokenManager) {
        this.userService = userService;
        this.jwtTokenManager = jwtTokenManager;
    }

    @Override
    public String signIn(String userName, String password) {
        if (userService.validateCredentials(userName, password)){
            return jwtTokenManager.generateToken(userName).orElse(null);
        }

        throw new InvalidCredentials(DhUserStatus.BAD_CREDENTIALS.getCode(), null);
    }

    @Override
    public boolean singUp(SaveUserDto user) {
        return userService.saveUser(user).isPresent();
    }

    @Override
    public String signUpAndSingIn(SaveUserDto user) {
        if (userService.saveUser(user).isPresent())
            return jwtTokenManager.generateToken(user.getUserName()).orElse(null);

        return null;
    }

    @Override
    public boolean validateToken(String token) {
        return jwtTokenManager.validateToken(token);
    }
}
