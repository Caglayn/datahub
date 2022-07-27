package com.c8n.service.impl;

import com.c8n.constants.DhUserStatus;
import com.c8n.exception.UserNameAlreadyInUse;
import com.c8n.model.entity.DhUser;
import com.c8n.repository.DhUserRepository;
import com.c8n.service.DhUserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DhUserServiceImpl implements DhUserService {
    private final DhUserRepository userRepository;

    public DhUserServiceImpl(DhUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public DhUser saveUser(DhUser user) {
        if(userRepository.existsById(user.getUserName()))
            throw new UserNameAlreadyInUse(DhUserStatus.NAME_IN_USE.getCode(), user.getUserName());

        return userRepository.save(user);
    }

    @Override
    public DhUser updateUser(DhUser user) {
        return null;
    }

    @Override
    public DhUser getUser(String userName) {
        return null;
    }

    @Override
    public List<DhUser> getAllUsers() {
        return null;
    }

    @Override
    public boolean validateCredentials(String userName, String password) {
        return false;
    }
}
