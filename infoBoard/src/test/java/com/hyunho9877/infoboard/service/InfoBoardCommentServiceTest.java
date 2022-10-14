package com.hyunho9877.infoboard.service;

import com.hyunho9877.infoboard.domain.InfoBoardComment;
import com.hyunho9877.infoboard.dto.InfoBoardCommentDto;
import com.hyunho9877.infoboard.repository.InfoBoardCommentRepository;
import com.hyunho9877.infoboard.service.interfaces.InfoBoardCommentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class InfoBoardCommentServiceTest {

    @Autowired
    private InfoBoardCommentService commentService;
    @Autowired
    private InfoBoardCommentRepository commentRepository;

    @Test
    @DirtiesContext
    void apply_basic() {
        InfoBoardCommentDto dto = new InfoBoardCommentDto(null, "2017138883", "test apply 1", 10001L);
        commentService.apply(dto);
        assertEquals(9, commentRepository.findAll().size());
    }

    @Test
    void apply_no_writer() {
        InfoBoardCommentDto dto = new InfoBoardCommentDto(null, null, "test apply 1", 10001L);
        assertThrows(IllegalArgumentException.class, () -> commentService.apply(dto));

    }

    @Test
    void apply_no_comment() {
        InfoBoardCommentDto dto = new InfoBoardCommentDto(null, "2017138883", null, 10001L);
        assertThrows(IllegalArgumentException.class, () -> commentService.apply(dto));
    }

    @Test
    void apply_no_article() {
        InfoBoardCommentDto dto = new InfoBoardCommentDto(null, "2017138883", "test apply 1", null);
        assertThrows(InvalidDataAccessApiUsageException.class, () -> commentService.apply(dto));
    }

    @Test
    void apply_empty_writer() {
        InfoBoardCommentDto dto = new InfoBoardCommentDto(null, "", "test apply 1", 10001L);
        assertThrows(IllegalArgumentException.class, () -> commentService.apply(dto));
    }

    @Test
    void apply_empty_comment() {
        InfoBoardCommentDto dto = new InfoBoardCommentDto(null, "2017138883", "test apply 1", 10001L);
        commentService.apply(dto);
        assertEquals(9, commentRepository.findAll().size());
    }


    @Test
    @DirtiesContext
    void delete_basic() {
        InfoBoardCommentDto dto = new InfoBoardCommentDto(20001L, "202011523", null,  10001L);
        commentService.delete(dto);
        assertTrue(commentRepository.findById(20001L).isEmpty());
    }

    @Test
    void delete_no_id() {
        InfoBoardCommentDto dto = new InfoBoardCommentDto(null, "202011523", null,  10001L);
        assertThrows(InvalidDataAccessApiUsageException.class, () -> commentService.delete(dto));
    }

    @Test
    void delete_no_writer() {
        InfoBoardCommentDto dto = new InfoBoardCommentDto(20001L, null, null,  10001L);
        assertThrows(IllegalArgumentException.class, () -> commentService.delete(dto));
    }

    @Test
    void delete_no_article() {
        InfoBoardCommentDto dto = new InfoBoardCommentDto(20001L, "202011523", null,  null);
        assertThrows(InvalidDataAccessApiUsageException.class, () -> commentService.delete(dto));
    }

    @Test
    void delete_empty_writer() {
        InfoBoardCommentDto dto = new InfoBoardCommentDto(20001L, "", null,  null);
        assertThrows(IllegalArgumentException.class, () -> commentService.delete(dto));
    }

    @Test
    @DirtiesContext
    void update_basic() {
        String updated = "updated";
        InfoBoardCommentDto dto = new InfoBoardCommentDto(20001L, "202011523", updated, 10001L);
        commentService.update(dto);
        InfoBoardComment article = commentRepository.findById(20001L).orElseThrow();
        assertEquals(updated, article.getComment());
    }

    @Test
    void update_no_id() {
        String updated = "updated";
        InfoBoardCommentDto dto = new InfoBoardCommentDto(null, "202011523", updated, 10001L);
        assertThrows(InvalidDataAccessApiUsageException.class, () -> commentService.update(dto));
    }

    @Test
    void update_no_writer() {
        String updated = "updated";
        InfoBoardCommentDto dto = new InfoBoardCommentDto(20001L, null, updated, 10001L);
        assertThrows(IllegalArgumentException.class, () -> commentService.update(dto));
    }

    @Test
    void update_no_comment() {
        String updated = "updated";
        InfoBoardCommentDto dto = new InfoBoardCommentDto(20001L, "202011523", null, 10001L);
        assertThrows(IllegalArgumentException.class, () -> commentService.update(dto));
    }

    @Test
    void update_no_article() {
        String updated = "updated";
        InfoBoardCommentDto dto = new InfoBoardCommentDto(20001L, "202011523", updated, null);
        commentService.update(dto);
        InfoBoardComment comment = commentRepository.findById(20001L).orElseThrow();
        assertEquals(updated, comment.getComment());
    }

    @Test
    void update_empty_writer() {
        String updated = "updated";
        InfoBoardCommentDto dto = new InfoBoardCommentDto(20001L, "", updated, 10001L);
        assertThrows(IllegalArgumentException.class, () -> commentService.update(dto));
    }

    @Test
    void update_empty_content() {
        String updated = "updated";
        InfoBoardCommentDto dto = new InfoBoardCommentDto(20001L, "202011523", "", 10001L);
        assertThrows(IllegalArgumentException.class, () -> commentService.update(dto));
    }
}