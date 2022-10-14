package com.hyunho9877.freeboard.utils.validator.empty_content;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public class EmptyContentValidator {
    public static void validate(String... values) throws IllegalArgumentException{
        boolean match = Arrays.stream(values)
                .anyMatch(s -> (s == null || s.equals("") || s.equals("\n") || s.equals("\t") || s.equals(" ")));
        if(match) throw new IllegalArgumentException("DTO contains invalid character like '\n', '\t' blank, empty string or null");
    }
}
