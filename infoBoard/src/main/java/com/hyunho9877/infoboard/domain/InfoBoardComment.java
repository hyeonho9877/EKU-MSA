package com.hyunho9877.infoboard.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter @ToString @Builder
@NoArgsConstructor @AllArgsConstructor
public class InfoBoardComment extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String writer;
    private String comment;
    @ManyToOne
    @JsonIgnore
    private InfoBoard article;
}
