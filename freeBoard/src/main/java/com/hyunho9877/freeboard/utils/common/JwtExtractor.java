package com.hyunho9877.freeboard.utils.common;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class JwtExtractor {

    public String getStudNo(Jwt jwt) {
        return jwt.getClaimAsString(JwtKeys.STUD_NO.getKey());
    }

    public String getName(Jwt jwt) {
        return jwt.getClaimAsString(JwtKeys.LAST_NAME.getKey()) + jwt.getClaimAsString(JwtKeys.FIRST_NAME.getKey());
    }

    public String getDepartment(Jwt jwt) {
        return jwt.getClaimAsString(JwtKeys.DEPT.getKey());
    }
}
