package com.hyunho9877.freeboard.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter @ToString @Builder
@NoArgsConstructor @AllArgsConstructor
public class FreeBoardComment extends BaseTime{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String writer;
    private String comment;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private FreeBoard article;
}
