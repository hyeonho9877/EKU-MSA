package com.hyunho9877.critic.service.interfaces;

import com.hyunho9877.critic.domain.Critic;
import com.hyunho9877.critic.dto.CriticDTO;

import java.util.List;
import java.util.NoSuchElementException;

public interface CriticService {
    CriticDTO apply(CriticDTO criticDTO);
    CriticDTO delete(CriticDTO criticDTO);
    CriticDTO update(CriticDTO criticDTO) throws NoSuchElementException;
    List<Critic> getRecent();
    List<Critic> get(String lectureName, String lectureProfessor);
}
