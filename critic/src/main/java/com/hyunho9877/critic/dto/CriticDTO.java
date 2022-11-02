package com.hyunho9877.critic.dto;

import com.hyunho9877.critic.domain.Grade;

public record CriticDTO(Long id, String writer, String content, Grade grade, Float star, String lectureName, String lectureProfessor) {

}
