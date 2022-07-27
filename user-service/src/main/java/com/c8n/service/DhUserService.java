package com.c8n.service;

import com.c8n.model.entity.DhUser;

import java.util.List;

public interface DhUserService {
    DhUser saveUser(DhUser user);
    DhUser updateUser(DhUser user);
    DhUser getUser(String userName);
    List<DhUser> getAllUsers();
    boolean validateCredentials(String userName, String password);
}
