package com.hyunho9877.freeboard.dto;

import com.hyunho9877.freeboard.utils.validator.number_only.NumberFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
public class FreeBoardCommentDTO {
    private Long id;
    @NumberFormat
    private String writer;
    private String comment;
    private Long articleID;
}