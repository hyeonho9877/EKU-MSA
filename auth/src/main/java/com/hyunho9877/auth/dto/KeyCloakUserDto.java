package com.hyunho9877.auth.dto;

import lombok.*;

import java.util.List;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class KeyCloakUserDto {
    private String firstName;
    private String lastName;
    private String email;
    private boolean enabled;
    private String username;
    private List<CredentialsDto> credentials;

    @Getter @Setter @ToString
    @AllArgsConstructor @NoArgsConstructor
    public static class CredentialsDto {
        private String value;
        private boolean temporary;
    }
}
