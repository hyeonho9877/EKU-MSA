package com.hyunho9877.auth.dto;

import lombok.Data;

@Data
public class Jwt {
    private String access_token;
    private int expires_in;
    private int refresh_expires_in;
    private String token_type;
    private int not_before_policy;
    private String scope;
}
