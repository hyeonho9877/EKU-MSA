package com.hyunho9877.doodle.service.interfaces;

import com.hyunho9877.doodle.domain.Doodle;
import com.hyunho9877.doodle.dto.DoodleDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DoodleService {
    DoodleDTO apply(DoodleDTO doodleDTO) throws IllegalArgumentException;

    DoodleDTO delete(DoodleDTO doodleDTO) throws IllegalArgumentException;

    void update(Long doodleId, String content) throws IllegalArgumentException;

    List<Doodle> get(String building, Pageable pageable);
}
