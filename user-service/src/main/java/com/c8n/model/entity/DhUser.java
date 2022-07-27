package com.c8n.model.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(value = "users")
public class DhUser {
    @Id
    private String userName;
    private String password;
    private String eMail;
    private Set<String> roles;
    private long createdDate;
    private boolean isActive;
    private boolean isLocked;
}