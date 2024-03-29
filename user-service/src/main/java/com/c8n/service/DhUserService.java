package com.c8n.service;

import com.c8n.model.entity.DhUser;
import com.c8n.model.request.SaveUserDto;
import com.c8n.model.response.DhUserDto;

import java.util.List;
import java.util.Optional;

public interface DhUserService {
    Optional<DhUser> saveUser(SaveUserDto user);
    DhUserDto getUser(String userName);
    List<DhUser> getAllUsers();
    boolean validateCredentials(String userName, String password);
}
