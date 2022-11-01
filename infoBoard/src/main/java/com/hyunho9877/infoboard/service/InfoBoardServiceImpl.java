package com.hyunho9877.infoboard.service;

import com.hyunho9877.infoboard.domain.InfoBoard;
import com.hyunho9877.infoboard.dto.InfoBoardDto;
import com.hyunho9877.infoboard.repository.InfoBoardRepository;
import com.hyunho9877.infoboard.service.interfaces.InfoBoardService;
import com.hyunho9877.infoboard.utils.common.WriterGenerator;
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
    private final WriterGenerator writerGenerator;

    @Override
    public List<InfoBoard> recent(String building) {
        validate(building);
        return boardRepository.findByBuilding(building);
    }

    @Override
    public void apply(InfoBoardDto dto, String studNo, String name) {
        validate(dto.content(), dto.building());

        InfoBoard article = InfoBoard.builder()
                .writer(writerGenerator.generate(studNo, name))
                .content(dto.content())
                .building(dto.building())
                .build();

        boardRepository.save(article);
    }

    @Override
    public void delete(InfoBoardDto dto) {
        validate(dto.building());
        boardRepository.deleteById(dto.id());
    }

    @Override
    public void update(InfoBoardDto dto) {
        validate(dto.building(), dto.content());
        InfoBoard article = boardRepository.findById(dto.id()).orElseThrow();
        article.setContent(dto.content());
    }
}
