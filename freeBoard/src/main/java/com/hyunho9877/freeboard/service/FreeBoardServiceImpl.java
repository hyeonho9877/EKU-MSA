package com.hyunho9877.freeboard.service;

import com.hyunho9877.freeboard.domain.FreeBoard;
import com.hyunho9877.freeboard.dto.FreeBoardDTO;
import com.hyunho9877.freeboard.repository.FreeBoardRepository;
import com.hyunho9877.freeboard.service.interfaces.FreeBoardService;
import com.hyunho9877.freeboard.utils.common.WriterGenerator;
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
    private final WriterGenerator writerGenerator;

    @Override
    public List<FreeBoardDTO> recent(String building) throws IllegalArgumentException{
        validate(building);
        return boardRepository.findByBuildingOrderByIdDesc(building).stream()
                .map(freeBoard -> new FreeBoardDTO(freeBoard.getId(), writerGenerator.generate(freeBoard.getWriter(), freeBoard.getDept()), freeBoard.getContent(), freeBoard.getComments(), freeBoard.getBuilding()))
                .toList();
    }

    @Override
    public FreeBoardDTO apply(FreeBoardDTO dto, String studNo, String dept) throws IllegalArgumentException {
        validate(dto.building(), dto.content());
        FreeBoard board = FreeBoard.builder()
                .content(dto.content())
                .writer(studNo)
                .dept(dept)
                .building(dto.building())
                .disabled(false)
                .build();
        boardRepository.save(board);
        return dto;
    }

    @Override
    public void delete(FreeBoardDTO dto, String writer) {
        FreeBoard article = boardRepository.findById(dto.id()).orElseThrow();
        if(!article.getWriter().equals(writer)) throw new IllegalStateException("user trying to delete article which is not written by user");
        boardRepository.delete(article);
    }

    @Override
    public FreeBoardDTO update(FreeBoardDTO dto, String writer) throws IllegalArgumentException{
        validate(dto.building(), dto.content());
        FreeBoard article = boardRepository.findById(dto.id()).orElseThrow();
        if(!article.getWriter().equals(writer)) throw new IllegalArgumentException("user trying to update article which is not written by user");
        article.setContent(dto.content());
        return dto;
    }
}
