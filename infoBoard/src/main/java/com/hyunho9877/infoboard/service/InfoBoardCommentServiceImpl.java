package com.hyunho9877.infoboard.service;

import com.hyunho9877.infoboard.domain.InfoBoard;
import com.hyunho9877.infoboard.domain.InfoBoardComment;
import com.hyunho9877.infoboard.dto.InfoBoardCommentDto;
import com.hyunho9877.infoboard.repository.InfoBoardCommentRepository;
import com.hyunho9877.infoboard.repository.InfoBoardRepository;
import com.hyunho9877.infoboard.service.interfaces.InfoBoardCommentService;
import com.hyunho9877.infoboard.utils.common.WriterGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.hyunho9877.infoboard.utils.validator.empty_content.EmptyContentValidator.validate;

@Service
@Transactional
@RequiredArgsConstructor
public class InfoBoardCommentServiceImpl implements InfoBoardCommentService {

    private final InfoBoardCommentRepository commentRepository;
    private final InfoBoardRepository boardRepository;
    private final WriterGenerator writerGenerator;

    @Override
    public List<InfoBoardComment> recent(Long articleId) {
        return commentRepository.findByArticle_Id(articleId);
    }

    @Override
    public void apply(InfoBoardCommentDto dto, String studNo, String name) {
        validate(dto.comment());

        InfoBoard article = boardRepository.findById(dto.article()).orElseThrow();

        InfoBoardComment comment = InfoBoardComment.builder()
                .writer(writerGenerator.generate(studNo, name))
                .comment(dto.comment())
                .article(article)
                .build();

        commentRepository.save(comment);
        article.setComments(article.getComments()+1);
        article.getCommentList().add(comment);
    }

    @Override
    public void delete(InfoBoardCommentDto dto) {
        InfoBoard article = boardRepository.findById(dto.article()).orElseThrow();
        commentRepository.deleteById(dto.id());
        article.setComments(article.getComments() - 1);
    }

    @Override
    public void update(InfoBoardCommentDto dto) {
        validate(dto.comment());
        InfoBoardComment comment = commentRepository.findById(dto.id()).orElseThrow();
        comment.setComment(dto.comment());
    }
}
