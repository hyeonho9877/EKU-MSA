package com.hyunho9877.critic.domain;

import com.hyunho9877.critic.utils.converter.GradeConverter;
import lombok.*;

import javax.persistence.*;

/**
 * 강의평가 DB 저장 객체
 */
@Entity
@Getter @Setter @ToString @Builder
@AllArgsConstructor @NoArgsConstructor
public class Critic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long criticId;
    @Column(nullable = false)
    private String content;
    @Column(nullable = false)
    @Convert(converter = GradeConverter.class)
    private Grade grade;
    @Column(nullable = false)
    private Float star;
    @Column(nullable = false)
    private Long writer;
    @Column(nullable = false)
    private String lectureName;
    @Column(nullable = false)
    private String lectureProfessor;
}
