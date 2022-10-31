package com.hyunho9877.critic.repository;

import com.hyunho9877.critic.domain.Critic;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CriticRepository extends JpaRepository<Critic, Long> {
    List<Critic> findByOrderByCriticIdDesc(Pageable pageable);
    List<Critic> findByLectureNameAndLectureProfessor(String lectureName, String lectureProfessor);
    void deleteByWriter(String writer);
}