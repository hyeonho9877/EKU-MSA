package com.hyunho9877.infoboard.service;

import com.hyunho9877.infoboard.domain.InfoBoard;
import com.hyunho9877.infoboard.domain.InfoBoardComment;
import com.hyunho9877.infoboard.dto.InfoBoardCommentDto;
import com.hyunho9877.infoboard.repository.InfoBoardCommentRepository;
import com.hyunho9877.infoboard.repository.InfoBoardRepository;
import com.hyunho9877.infoboard.service.interfaces.InfoBoardCommentService;
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

    @Override
    public List<InfoBoardComment> recent(Long articleId) {
        return commentRepository.findByArticle_Id(articleId);
    }

    @Override
    public void apply(InfoBoardCommentDto dto) {
        validate(dto.getWriter(), dto.getComment());

        InfoBoard article = boardRepository.findById(dto.getArticle()).orElseThrow();

        InfoBoardComment comment = InfoBoardComment.builder()
                .writer(dto.getWriter())
                .comment(dto.getComment())
                .article(article)
                .build();

        commentRepository.save(comment);
        article.setComments(article.getComments()+1);
        article.getCommentList().add(comment);
    }

    @Override
    public void delete(InfoBoardCommentDto dto) {
        validate(dto.getWriter());
        InfoBoard article = boardRepository.findById(dto.getArticle()).orElseThrow();
        commentRepository.deleteById(dto.getId());
        article.setComments(article.getComments() - 1);
    }

    @Override
    public void update(InfoBoardCommentDto dto) {
        validate(dto.getComment(), dto.getWriter());
        InfoBoardComment comment = commentRepository.findById(dto.getId()).orElseThrow();
        comment.setComment(dto.getComment());
    }
}
