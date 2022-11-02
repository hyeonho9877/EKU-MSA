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
    public List<InfoBoardDto> recent(String building) {
        validate(building);
        return boardRepository.findByBuilding(building).stream()
                .map(article -> new InfoBoardDto(article.getId(), article.getBuilding(), article.getContent(), article.getComments(), writerGenerator.generate(article.getWriter(), article.getDept())))
                .toList();
    }

    @Override
    public void apply(InfoBoardDto dto, String studNo, String dept) {
        validate(dto.content(), dto.building());

        InfoBoard article = InfoBoard.builder()
                .writer(studNo)
                .dept(dept)
                .content(dto.content())
                .building(dto.building())
                .build();

        boardRepository.save(article);
    }

    @Override
    public void delete(InfoBoardDto dto, String writer) {
        validate(dto.building());
        InfoBoard article = boardRepository.findById(dto.id()).orElseThrow();
        if(!article.getWriter().equals(writer))
            throw new IllegalStateException("user trying to delete article which is not written by user");
        boardRepository.delete(article);
    }

    @Override
    public void update(InfoBoardDto dto, String writer) {
        validate(dto.building(), dto.content());
        InfoBoard article = boardRepository.findById(dto.id()).orElseThrow();
        if(!article.getWriter().equals(writer))
            throw new IllegalStateException("user trying to update article which is not written by user");
        article.setContent(dto.content());
    }
}
