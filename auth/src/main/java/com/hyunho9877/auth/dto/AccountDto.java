package com.hyunho9877.auth.dto;

import lombok.Data;

@Data
public class AccountDto {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
