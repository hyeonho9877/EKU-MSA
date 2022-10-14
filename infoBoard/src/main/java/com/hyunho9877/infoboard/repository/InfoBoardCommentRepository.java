package com.hyunho9877.infoboard.repository;

import com.hyunho9877.infoboard.domain.InfoBoardComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InfoBoardCommentRepository extends JpaRepository<InfoBoardComment, Long> {
    List<InfoBoardComment> findByArticle_Id(Long articleId);
}
