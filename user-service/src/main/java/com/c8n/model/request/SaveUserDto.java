package com.c8n.model.request;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaveUserDto {
    private String userName;
    private String password;
    private String eMail;
}
