package com.c8n.service.impl;

import com.c8n.constants.DhUserStatus;
import com.c8n.exception.UserNameAlreadyInUse;
import com.c8n.model.entity.DhUser;
import com.c8n.model.request.SaveUserDto;
import com.c8n.model.response.DhUserDto;
import com.c8n.repository.DhUserRepository;
import com.c8n.service.DhUserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class DhUserServiceImpl implements DhUserService {
    private final DhUserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public DhUserServiceImpl(DhUserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<DhUser> saveUser(SaveUserDto user) {
        if(userRepository.existsById(user.getUserName()))
            throw new UserNameAlreadyInUse(DhUserStatus.NAME_IN_USE.getCode(), user.getUserName());

        this.encodePassword(user);
        DhUser dhUser = DhUser.builder()
                .userName(user.getUserName())
                .password(user.getPassword())
                .eMail(user.getEMail())
                .isActive(true)
                .isLocked(false)
                .roles(new HashSet<>(Arrays.asList("1")))
                .createdDate(System.currentTimeMillis())
                .build();

        return Optional.of(userRepository.save(dhUser));
    }

    @Override
    public DhUserDto getUser(String userName) {
        Optional<DhUser> userOptional = userRepository.findById(userName);
        if (userOptional.isPresent()){
            DhUser user = userOptional.get();
            return DhUserDto.builder()
                    .userName(user.getUserName())
                    .eMail(user.getEMail())
                    .roles(user.getRoles())
                    .createdDate(user.getCreatedDate())
                    .isActive(user.isActive())
                    .isLocked(user.isLocked())
                    .build();
        } else {
            return null;
        }
    }

    @Override
    public List<DhUser> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public boolean validateCredentials(String userName, String password) {
        Optional<DhUser> optionalDhUser = userRepository.findById(userName);
        if (optionalDhUser.isPresent()){
            DhUser user = optionalDhUser.get();
            return passwordEncoder.matches(password, user.getPassword());
        } else {
            return false;
        }
    }

    private void encodePassword(SaveUserDto user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }

}
