package com.hyunho9877.infoboard.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter @ToString @Builder
@NoArgsConstructor @AllArgsConstructor
public class InfoBoard extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String writer;
    @Column(nullable = false)
    private String dept;
    @Column(nullable = false)
    private String building;
    @Column(nullable = false)
    private String content;
    @Column(nullable = false)
    private long comments;
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    @JsonIgnore @ToString.Exclude
    private List<InfoBoardComment> commentList;

    @PrePersist
    private void prePersist() {
        this.comments = 0;
    }
}
