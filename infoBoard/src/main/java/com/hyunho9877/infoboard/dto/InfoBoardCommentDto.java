package com.hyunho9877.infoboard.dto;

import com.hyunho9877.infoboard.utils.validator.number_only.NumberFormat;
import lombok.Data;


@Data
public class InfoBoardCommentDto {
    private Long id;
    @NumberFormat
    private String writer;
    private String comment;
    private Long article;
}
