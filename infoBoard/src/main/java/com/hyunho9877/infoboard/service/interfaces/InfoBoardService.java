package com.hyunho9877.infoboard.service.interfaces;

import com.hyunho9877.infoboard.dto.InfoBoardDto;

import java.util.List;

public interface InfoBoardService extends Service<InfoBoardDto> {
    List<InfoBoardDto> recent(String building);
}
