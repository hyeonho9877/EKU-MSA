package com.hyunho9877.freeboard.service;

import com.hyunho9877.freeboard.domain.FreeBoard;
import com.hyunho9877.freeboard.dto.FreeBoardDTO;
import com.hyunho9877.freeboard.repository.FreeBoardRepository;
import com.hyunho9877.freeboard.service.interfaces.FreeBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.hyunho9877.freeboard.utils.validator.empty_content.EmptyContentValidator.validate;

@Service
@Transactional
@RequiredArgsConstructor
public class FreeBoardServiceImpl implements FreeBoardService {

    private final FreeBoardRepository boardRepository;

    @Override
    public List<FreeBoard> recent(String building) throws IllegalArgumentException{
        validate(building);
        return boardRepository.findByBuildingOrderByIdDesc(building);
    }

    @Override
    public FreeBoardDTO apply(FreeBoardDTO dto) throws IllegalArgumentException {
        validate(dto.getWriter(), dto.getBuilding(), dto.getContent());
        FreeBoard board = FreeBoard.builder()
                .content(dto.getContent())
                .writer(dto.getWriter())
                .building(dto.getBuilding())
                .build();
        boardRepository.save(board);
        return dto;
    }

    @Override
    public void delete(FreeBoardDTO dto) {
        boardRepository.deleteById(dto.getId());
    }

    @Override
    public FreeBoardDTO update(FreeBoardDTO dto) throws IllegalArgumentException{
        validate(dto.getBuilding(), dto.getContent(), dto.getWriter());
        FreeBoard board = boardRepository.findById(dto.getId()).orElseThrow();
        board.setContent(dto.getContent());
        return dto;
    }
}
