package com.hyunho9877.critic.service;

import com.hyunho9877.critic.domain.Critic;
import com.hyunho9877.critic.domain.Grade;
import com.hyunho9877.critic.dto.CriticDTO;
import com.hyunho9877.critic.repository.CriticRepository;
import com.hyunho9877.critic.service.interfaces.CriticService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class CriticServiceImpl implements CriticService {

    private final CriticRepository criticRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override

    public CriticDTO apply(CriticDTO criticDTO) {

        Critic critic = Critic.builder()
                .content(criticDTO.getContent())
                .grade(criticDTO.getGrade())
                .star(criticDTO.getStar())
                .lectureName(criticDTO.getLectureName())
                .lectureProfessor(criticDTO.getLectureProfessor())
                .writer(criticDTO.getWriter())
                .build();

        criticRepository.save(critic);

        return criticDTO;
    }

    @Override
    public CriticDTO delete(CriticDTO criticDTO) {
        criticRepository.deleteById(criticDTO.getCriticId());
        return criticDTO;
    }

    @Override
    @Transactional
    public CriticDTO update(CriticDTO criticDTO) throws NoSuchElementException {
        Critic critic = criticRepository.findById(criticDTO.getCriticId()).orElseThrow();

        String content = criticDTO.getContent();
        if(!content.isEmpty()) critic.setContent(content);

        Grade grade = criticDTO.getGrade();
        if(!(grade == null)) critic.setGrade(grade);

        Float star = criticDTO.getStar();
        if(!(star==null)) critic.setStar(star);

        return criticDTO;
    }


    @Override
    public List<Critic> getRecent() {
        return criticRepository.findByOrderByCriticIdDesc(Pageable.ofSize(10));
    }

    @Override
    public List<Critic> get(String lectureName, String lectureProfessor) {
        return criticRepository.findByLectureNameAndLectureProfessor(lectureName, lectureProfessor);
    }
}
