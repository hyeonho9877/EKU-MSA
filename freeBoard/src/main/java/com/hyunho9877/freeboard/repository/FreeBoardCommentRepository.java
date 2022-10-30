package com.hyunho9877.freeboard.repository;

import com.hyunho9877.freeboard.domain.FreeBoard;
import com.hyunho9877.freeboard.domain.FreeBoardComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FreeBoardCommentRepository extends JpaRepository<FreeBoardComment, Long> {
    List<FreeBoardComment> findByArticleOrderByIdDesc(FreeBoard article);

    void deleteByWriter(String username);
}