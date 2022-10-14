package com.hyunho9877.infoboard.dto;

import com.hyunho9877.infoboard.utils.validator.number_only.NumberFormat;
import lombok.Data;

import javax.persistence.PrePersist;

@Data
public class InfoBoardDto {
    private Long id;
    @NumberFormat
    private String writer;
    @NumberFormat
    private String building;
    private String content;
    private long comments;
}
