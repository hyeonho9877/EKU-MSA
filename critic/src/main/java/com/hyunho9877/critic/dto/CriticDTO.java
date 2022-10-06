package com.hyunho9877.critic.dto;

import com.hyunho9877.critic.domain.Grade;
import lombok.Data;

@Data
public class CriticDTO {
    private Long criticId;
    private Long writer;
    private String content;
    private Grade grade;
    private Float star;
    private String lectureName;
    private String lectureProfessor;
}
