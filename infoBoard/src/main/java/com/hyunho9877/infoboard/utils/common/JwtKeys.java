package com.hyunho9877.infoboard.utils.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum JwtKeys {

    STUD_NO("preferred_username"),
    FIRST_NAME("firstName"),
    LAST_NAME("lastName"),
    DEPT("department");

    private final String key;
}
