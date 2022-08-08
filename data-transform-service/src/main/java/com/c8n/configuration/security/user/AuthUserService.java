package com.c8n.configuration.security.user;

import com.c8n.model.Sessions;
import com.c8n.model.dto.DhUserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.c8n.constants.RestApiUrls.*;

@Service
public class AuthUserService implements UserDetailsService {

    private final ObjectMapper objectMapper;

    private final Sessions sessions;

    public AuthUserService(ObjectMapper objectMapper, Sessions sessions) {
        this.objectMapper = objectMapper;
        this.sessions = sessions;
    }

    @Value("${SERVER_INTERNAL_IP}")
    private String serverAddress;

    @Value("${USER_SERVICE_PORT}")
    private String userServicePort;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserDetails user = sessions.getUsers().get(userName);

        if (user != null)
            return user;
        else
            return getAuthUserFromUserService(userName);
    }

    private AuthUser getAuthUserFromUserService(String userName){
        StringBuilder urlString = new StringBuilder();
        urlString.append("http://")
                .append(serverAddress)
                .append(":")
                .append(userServicePort)
                .append(API)
                .append(VERSION)
                .append(AUTH)
                .append(USERDETAIL)
                .append("?username=")
                .append(userName);

        try {
            URL url = new URL(urlString.toString());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("accept", "application/json");
            InputStream inputStream = connection.getInputStream();
            DhUserDto userDto = (DhUserDto) objectMapper.readValue(inputStream, DhUserDto.class);
            inputStream.close();
            connection.disconnect();
            return AuthUser.builder()
                    .eMail(userDto.getEMail())
                    .userName(userDto.getUserName())
                    .roles(userDto.getRoles())
                    .createdDate(userDto.getCreatedDate())
                    .isActive(userDto.isActive())
                    .isLocked(userDto.isLocked())
                    .build();
        } catch (Exception e) {
            return null;
        }
    }
}
