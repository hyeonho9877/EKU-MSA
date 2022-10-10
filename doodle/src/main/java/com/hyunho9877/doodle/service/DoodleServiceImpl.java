package com.hyunho9877.doodle.service;

import com.hyunho9877.doodle.domain.Doodle;
import com.hyunho9877.doodle.dto.DoodleDTO;
import com.hyunho9877.doodle.repository.DoodleRepository;
import com.hyunho9877.doodle.service.interfaces.DoodleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DoodleServiceImpl implements DoodleService {

    private final DoodleRepository doodleRepository;

    @Override
    public DoodleDTO apply(DoodleDTO doodleDTO) throws IllegalArgumentException{
        checkContentLength(doodleDTO.getContent());
        Doodle doodle = Doodle.builder()
                .content(doodleDTO.getContent())
                .building(doodleDTO.getBuilding())
                .build();
        doodleRepository.save(doodle);
        return doodleDTO;
    }

    private void checkContentLength(String content) throws IllegalArgumentException {
        if(content.length() > 50) throw new IllegalArgumentException("Length of Doodle Content is over 50");
        else if(content.equals("")) throw new IllegalArgumentException("Length of Doodle Content must not be 0");
    }

    @Override
    public DoodleDTO delete(DoodleDTO doodleDTO) throws IllegalArgumentException {
        doodleRepository.deleteById(doodleDTO.getDoodleId());
        return doodleDTO;
    }

    @Override
    @Transactional
    public void update(Long doodleId, String content) throws IllegalArgumentException {
        checkContentLength(content);
        Doodle doodle = doodleRepository.findById(doodleId).orElseThrow();
        doodle.setContent(content);
    }

    @Override
    public List<Doodle> get(String building, Pageable pageable) {
        return doodleRepository.findByBuildingOrderByDoodleIdDesc(building, pageable);
    }
}
