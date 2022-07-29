package com.c8n.service;

import com.c8n.model.entity.DhUser;
import com.c8n.model.request.SaveUserDto;

public interface AuthService {
    String signIn(String userName, String password);
    boolean singUp(SaveUserDto user);
    String signUpAndSingIn(SaveUserDto user);
    boolean validateToken(String token);
}
