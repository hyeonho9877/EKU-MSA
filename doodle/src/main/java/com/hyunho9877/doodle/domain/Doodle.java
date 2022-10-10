package com.hyunho9877.doodle.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter @ToString @Builder
@NoArgsConstructor @AllArgsConstructor
public class Doodle extends BaseTime{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long doodleId;
    @Column(nullable = false,length = 50)
    private String content;
    @Column(nullable = false)
    private String building;
}
