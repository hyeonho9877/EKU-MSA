package com.hyunho9877.freeboard.service.interfaces;

import com.hyunho9877.freeboard.domain.FreeBoard;
import com.hyunho9877.freeboard.dto.FreeBoardDTO;

import java.util.List;

public interface FreeBoardService extends Service<FreeBoardDTO>{
    List<FreeBoard> recent(String building);
}
