package com.c8n.model.dto;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DhUserDto {
    private String userName;
    private String eMail;
    private String token;
    private Set<String> roles;
    private long createdDate;
    private boolean isActive;
    private boolean isLocked;
}
