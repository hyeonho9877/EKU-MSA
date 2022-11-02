package com.hyunho9877.freeboard.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;

@Entity
@Getter @Setter @ToString @Builder
@NoArgsConstructor @AllArgsConstructor
@SQLDelete(sql = "update free_board_comment set disabled = true where id = ?")
public class FreeBoardComment extends BaseTime{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String writer;
    @Column(nullable = false)
    private String dept;
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @ToString.Exclude
    private FreeBoard article;

    private boolean disabled;
}
