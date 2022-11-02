package com.hyunho9877.critic.service;

import com.hyunho9877.critic.domain.Critic;
import com.hyunho9877.critic.domain.Grade;
import com.hyunho9877.critic.dto.CriticDTO;
import com.hyunho9877.critic.repository.CriticRepository;
import com.hyunho9877.critic.service.interfaces.CriticService;
import com.hyunho9877.critic.utils.common.WriterGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CriticServiceImpl implements CriticService {

    private final CriticRepository criticRepository;
    private final WriterGenerator writerGenerator;

    @Override
    public CriticDTO apply(CriticDTO criticDTO, String studNo, String dept) {

        Critic critic = Critic.builder()
                .content(criticDTO.content())
                .grade(criticDTO.grade())
                .star(criticDTO.star())
                .lectureName(criticDTO.lectureName())
                .lectureProfessor(criticDTO.lectureProfessor())
                .writer(studNo)
                .dept(dept)
                .build();

        criticRepository.save(critic);

        return criticDTO;
    }

    @Override
    public CriticDTO delete(CriticDTO criticDTO, String writer) {
        Critic critic = criticRepository.findById(criticDTO.id()).orElseThrow();
        if(!critic.getWriter().equals(writer))
            throw new IllegalStateException("user trying to delete critic which is not written by user");
        criticRepository.delete(critic);
        return criticDTO;
    }

    @Override
    public CriticDTO update(CriticDTO criticDTO, String writer) throws NoSuchElementException {
        Critic critic = criticRepository.findById(criticDTO.id()).orElseThrow();
        if(!critic.getWriter().equals(writer))
            throw new IllegalStateException("user trying to update article which is not written by user");

        String content = criticDTO.content();
        if (!content.isEmpty()) critic.setContent(content);

        Grade grade = criticDTO.grade();
        if (!(grade == null)) critic.setGrade(grade);

        Float star = criticDTO.star();
        if (!(star == null)) critic.setStar(star);

        return criticDTO;
    }


    @Override
    public List<CriticDTO> getRecent(Pageable pageable) {
        return criticRepository.findByOrderByCriticIdDesc(pageable)
                .stream()
                .map(critic -> new CriticDTO(critic.getId(), writerGenerator.generate(critic.getWriter(), critic.getDept()), critic.getContent(), critic.getGrade(), critic.getStar(), critic.getLectureName(), critic.getLectureProfessor()))
                .toList();
    }

    @Override
    public List<CriticDTO> get(String lectureName, String lectureProfessor, Pageable pageable) {
        return criticRepository.findByLectureNameAndLectureProfessor(lectureName, lectureProfessor, pageable)
                .stream()
                .map(critic -> new CriticDTO(critic.getId(), writerGenerator.generate(critic.getWriter(), critic.getDept()), critic.getContent(), critic.getGrade(), critic.getStar(), critic.getLectureName(), critic.getLectureProfessor()))
                .toList();
    }
}
