package com.hyunho9877.infoboard.service.interfaces;

import com.hyunho9877.infoboard.dto.InfoBoardCommentDto;

import java.util.List;

public interface InfoBoardCommentService extends Service<InfoBoardCommentDto> {
    List<InfoBoardCommentDto> recent(Long articleId);
}
