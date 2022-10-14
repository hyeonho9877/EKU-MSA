package com.hyunho9877.infoboard.repository;

import com.hyunho9877.infoboard.domain.InfoBoardComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InfoBoardCommentRepository extends JpaRepository<InfoBoardComment, Long> {
}
