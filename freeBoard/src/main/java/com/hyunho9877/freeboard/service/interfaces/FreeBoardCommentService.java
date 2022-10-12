package com.hyunho9877.freeboard.service.interfaces;

import com.hyunho9877.freeboard.domain.FreeBoardComment;
import com.hyunho9877.freeboard.dto.FreeBoardCommentDTO;

import java.util.List;

public interface FreeBoardCommentService extends Service<FreeBoardCommentDTO> {
    List<FreeBoardComment> recent(Long articleID);
}
