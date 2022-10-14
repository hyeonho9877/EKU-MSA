package com.hyunho9877.infoboard.service;

import com.hyunho9877.infoboard.domain.InfoBoard;
import com.hyunho9877.infoboard.dto.InfoBoardDto;
import com.hyunho9877.infoboard.repository.InfoBoardRepository;
import com.hyunho9877.infoboard.service.interfaces.InfoBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.hyunho9877.infoboard.utils.validator.empty_content.EmptyContentValidator.validate;

@Service
@Transactional
@RequiredArgsConstructor
public class InfoBoardServiceImpl implements InfoBoardService {

    private final InfoBoardRepository boardRepository;

    @Override
    public List<InfoBoard> recent(String building) {
        validate(building);
        return boardRepository.findByBuilding(building);
    }

    @Override
    public void apply(InfoBoardDto dto) {
        validate(dto.getWriter(), dto.getContent(), dto.getBuilding());

        InfoBoard article = InfoBoard.builder()
                .writer(dto.getWriter())
                .content(dto.getContent())
                .building(dto.getBuilding())
                .build();

        boardRepository.save(article);
    }

    @Override
    public void delete(InfoBoardDto dto) {
        validate(dto.getWriter(), dto.getBuilding());
        boardRepository.deleteById(dto.getId());
    }

    @Override
    public void update(InfoBoardDto dto) {
        validate(dto.getWriter(), dto.getBuilding(), dto.getContent());
        InfoBoard article = boardRepository.findById(dto.getId()).orElseThrow();
        article.setContent(dto.getContent());
    }
}
