package com.hyunho9877.infoboard.service.interfaces;

import com.hyunho9877.infoboard.domain.InfoBoardComment;
import com.hyunho9877.infoboard.dto.InfoBoardCommentDto;

import java.util.List;

public interface InfoBoardCommentService extends Service<InfoBoardCommentDto> {
    List<InfoBoardComment> recent(Long articleId);
}
