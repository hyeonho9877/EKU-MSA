package com.hyunho9877.infoboard.dto;

import com.hyunho9877.infoboard.utils.validator.number_only.NumberFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class InfoBoardDto {
    private Long id;
    @NumberFormat
    private String writer;
    @NumberFormat
    private String building;
    private String content;
    private long comments;
}
