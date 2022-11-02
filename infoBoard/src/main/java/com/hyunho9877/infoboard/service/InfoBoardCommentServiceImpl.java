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
    public List<InfoBoardCommentDto> recent(Long articleId) {
        return commentRepository.findByArticle_Id(articleId).stream()
                .map(comment -> new InfoBoardCommentDto(comment.getId(), comment.getComment(), null, writerGenerator.generate(comment.getWriter(), comment.getDept())))
                .toList();
    }

    @Override
    public void apply(InfoBoardCommentDto dto, String studNo, String dept) {
        validate(dto.comment());

        InfoBoard article = boardRepository.findById(dto.article()).orElseThrow();

        InfoBoardComment comment = InfoBoardComment.builder()
                .writer(studNo)
                .dept(dept)
                .comment(dto.comment())
                .article(article)
                .build();

        commentRepository.save(comment);
        article.setComments(article.getComments()+1);
        article.getCommentList().add(comment);
    }

    @Override
    public void delete(InfoBoardCommentDto dto, String writer) {
        InfoBoard article = boardRepository.findById(dto.article()).orElseThrow();
        InfoBoardComment comment = commentRepository.findById(dto.id()).orElseThrow();
        if(!comment.getWriter().equals(writer))
            throw new IllegalStateException("user trying to delete comment which is not written by user");
        commentRepository.delete(comment);
        article.setComments(article.getComments() - 1);
    }

    @Override
    public void update(InfoBoardCommentDto dto, String writer) {
        validate(dto.comment());
        InfoBoardComment comment = commentRepository.findById(dto.id()).orElseThrow();
        if(!comment.getWriter().equals(writer))
            throw new IllegalStateException("user trying to delete article which is not written by user");
        comment.setComment(dto.comment());
    }
}
