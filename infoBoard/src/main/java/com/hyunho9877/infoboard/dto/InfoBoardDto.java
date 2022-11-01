package com.hyunho9877.infoboard.dto;

import com.hyunho9877.infoboard.utils.validator.number_only.NumberFormat;

public record InfoBoardDto(Long id, @NumberFormat String building, String content, Long comments) {

}
