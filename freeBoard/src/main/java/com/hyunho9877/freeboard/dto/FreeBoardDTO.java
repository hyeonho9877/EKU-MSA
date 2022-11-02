package com.hyunho9877.freeboard.dto;

import com.hyunho9877.freeboard.utils.validator.number_only.NumberFormatValidator;

public record FreeBoardDTO(Long id, String writer, String content, Integer comments, String building) {
    public FreeBoardDTO {
        if(!NumberFormatValidator.isValid(building))
            throw new IllegalArgumentException("building must contain only number");
    }
}
