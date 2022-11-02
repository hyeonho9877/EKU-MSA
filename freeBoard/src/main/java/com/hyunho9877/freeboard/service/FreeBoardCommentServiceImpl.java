package com.hyunho9877.freeboard.service;

import com.hyunho9877.freeboard.domain.FreeBoard;
import com.hyunho9877.freeboard.domain.FreeBoardComment;
import com.hyunho9877.freeboard.dto.FreeBoardCommentDTO;
import com.hyunho9877.freeboard.repository.FreeBoardCommentRepository;
import com.hyunho9877.freeboard.repository.FreeBoardRepository;
import com.hyunho9877.freeboard.service.interfaces.FreeBoardCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.hyunho9877.freeboard.utils.validator.empty_content.EmptyContentValidator.validate;

@Service
@Transactional
@RequiredArgsConstructor
public class FreeBoardCommentServiceImpl implements FreeBoardCommentService {


    private final FreeBoardRepository boardRepository;
    private final FreeBoardCommentRepository commentRepository;

    @Override
    public List<FreeBoardComment> recent(Long articleID) throws IllegalArgumentException {
        if(articleID == null) throw new IllegalArgumentException("articleID must not be null");
        FreeBoard board = boardRepository.getArticleWithComments(articleID);
        return board.getCommentList();
    }

    @Override
    public FreeBoardCommentDTO apply(FreeBoardCommentDTO dto) throws IllegalArgumentException, InvalidDataAccessApiUsageException{
        validate(dto.comment());
        FreeBoard board = boardRepository.findById(dto.articleId()).orElseThrow();
        FreeBoardComment build = FreeBoardComment.builder()
                .writer(dto.getWriter())
                .comment(dto.comment())
                .article(board)
                .disabled(false)
                .build();
        FreeBoardComment comment = commentRepository.save(build);
        board.setComments(board.getComments()+1);
        board.getCommentList().add(comment);
        return dto;
    }

    @Override
    public void delete(FreeBoardCommentDTO dto) throws InvalidDataAccessApiUsageException {
        FreeBoard board = boardRepository.findById(dto.articleId()).orElseThrow();
        commentRepository.deleteById(dto.id());
        board.setComments(board.getComments()-1);
    }

    @Override
    public FreeBoardCommentDTO update(FreeBoardCommentDTO dto) {
        validate(dto.comment());
        FreeBoardComment comment = commentRepository.findById(dto.id()).orElseThrow();
        comment.setComment(dto.comment());
        return dto;
    }
}
