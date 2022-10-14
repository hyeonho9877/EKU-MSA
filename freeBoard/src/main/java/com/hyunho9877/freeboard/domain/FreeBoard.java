package com.hyunho9877.freeboard.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter @ToString @Builder
@NoArgsConstructor @AllArgsConstructor
public class FreeBoard extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String writer;
    @Column(nullable = false)
    private String content;
    private Integer comments;
    @Column(nullable = false)
    private String building;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "article")
    @JsonIgnore
    private List<FreeBoardComment> commentList;

    @PrePersist
    public void prePersist() {
        this.comments = 0;
    }
}
