package com.hyunho9877.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
public class KeycloakTokenDto {
    private String client_id;
    private String client_secret;
    private String grant_type;
}
