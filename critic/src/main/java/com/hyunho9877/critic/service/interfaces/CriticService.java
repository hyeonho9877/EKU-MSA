package com.hyunho9877.critic.service.interfaces;

import com.hyunho9877.critic.dto.CriticDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.NoSuchElementException;

public interface CriticService {
    CriticDTO apply(CriticDTO criticDTO, String studNo, String dept);
    CriticDTO delete(CriticDTO criticDTO, String writer);
    CriticDTO update(CriticDTO criticDTO, String writer) throws NoSuchElementException;
    List<CriticDTO> getRecent(Pageable pageable);
    List<CriticDTO> get(String lectureName, String lectureProfessor, Pageable pageable);
}
