package com.c8n.model;

import com.c8n.configuration.security.user.AuthUser;
import lombok.*;

import java.util.Map;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Sessions {
    Map<String, AuthUser> users;
}
