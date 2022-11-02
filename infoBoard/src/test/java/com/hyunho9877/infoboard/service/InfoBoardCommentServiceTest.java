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
        InfoBoardCommentDto dto = new InfoBoardCommentDto(null, "test apply 1", 10001L, null);
        commentService.apply(dto, "201713883", "신현호");
        assertEquals(9, commentRepository.findAll().size());
    }

    @Test
    void apply_no_writer() {
        // INVALID CASE
        /*InfoBoardCommentDto dto = new InfoBoardCommentDto(null, "test apply 1", 10001L);
        assertThrows(IllegalArgumentException.class, () -> commentService.apply(dto, "201713883", "신현호"));*/
    }

    @Test
    void apply_no_comment() {
        InfoBoardCommentDto dto = new InfoBoardCommentDto(null, null, 10001L, null);
        assertThrows(IllegalArgumentException.class, () -> commentService.apply(dto, "201713883", "신현호"));
    }

    @Test
    void apply_no_article() {
        InfoBoardCommentDto dto = new InfoBoardCommentDto(null, "test apply 1", null, null);
        assertThrows(InvalidDataAccessApiUsageException.class, () -> commentService.apply(dto, "201713883", "신현호"));
    }

    @Test
    void apply_empty_writer() {
        // INVALID CASE
        /*InfoBoardCommentDto dto = new InfoBoardCommentDto(null, "test apply 1", 10001L);
        assertThrows(IllegalArgumentException.class, () -> commentService.apply(dto, "201713883", "신현호"));*/
    }

    @Test
    void apply_empty_comment() {
        InfoBoardCommentDto dto = new InfoBoardCommentDto(null, "test apply 1", 10001L, null);
        commentService.apply(dto, "201713883", "신현호");
        assertEquals(9, commentRepository.findAll().size());
    }


    @Test
    @DirtiesContext
    void delete_basic() {
        InfoBoardCommentDto dto = new InfoBoardCommentDto(20001L, null,  10001L, null);
        commentService.delete(dto, "202011523");
        assertTrue(commentRepository.findById(20001L).isEmpty());
    }

    @Test
    void delete_no_id() {
        InfoBoardCommentDto dto = new InfoBoardCommentDto(null, null,  10001L, null);
        assertThrows(InvalidDataAccessApiUsageException.class, () -> commentService.delete(dto, "202011523"));
    }

    @Test
    void delete_no_writer() {
        // INVALID CASE
        /*InfoBoardCommentDto dto = new InfoBoardCommentDto(20001L, null,  10001L);
        assertThrows(IllegalArgumentException.class, () -> commentService.delete(dto));*/
    }

    @Test
    void delete_no_article() {
        InfoBoardCommentDto dto = new InfoBoardCommentDto(20001L, null,  null, null);
        assertThrows(InvalidDataAccessApiUsageException.class, () -> commentService.delete(dto, "202011523"));
    }

    @Test
    void delete_empty_writer() {
        // INVALID CASE
        /*InfoBoardCommentDto dto = new InfoBoardCommentDto(20001L, null,  null);
        assertThrows(IllegalArgumentException.class, () -> commentService.delete(dto));*/
    }

    @Test
    void delete_with_no_authorization() {
        InfoBoardCommentDto dto = new InfoBoardCommentDto(20001L, null,  10001L, null);
        assertThrows(IllegalStateException.class, () -> commentService.delete(dto, "201713883"));
    }

    @Test
    @DirtiesContext
    void update_basic() {
        String updated = "updated";
        InfoBoardCommentDto dto = new InfoBoardCommentDto(20001L, updated, 10001L, null);
        commentService.update(dto, "202011523");
        InfoBoardComment article = commentRepository.findById(20001L).orElseThrow();
        assertEquals(updated, article.getComment());
    }

    @Test
    void update_no_id() {
        String updated = "updated";
        InfoBoardCommentDto dto = new InfoBoardCommentDto(null, updated, 10001L, null);
        assertThrows(InvalidDataAccessApiUsageException.class, () -> commentService.update(dto, "202011523"));
    }

    @Test
    void update_no_writer() {
        // INVALID CASE
        /*String updated = "updated";
        InfoBoardCommentDto dto = new InfoBoardCommentDto(20001L, updated, 10001L);
        assertThrows(IllegalArgumentException.class, () -> commentService.update(dto));*/
    }

    @Test
    void update_no_comment() {
        String updated = "updated";
        InfoBoardCommentDto dto = new InfoBoardCommentDto(20001L, null, 10001L, null);
        assertThrows(IllegalArgumentException.class, () -> commentService.update(dto, "202011523"));
    }

    @Test
    void update_no_article() {
        String updated = "updated";
        InfoBoardCommentDto dto = new InfoBoardCommentDto(20001L, updated, null, null);
        commentService.update(dto, "202011523");
        InfoBoardComment comment = commentRepository.findById(20001L).orElseThrow();
        assertEquals(updated, comment.getComment());
    }

    @Test
    void update_empty_writer() {
        // INVALID CASE
        /*String updated = "updated";
        InfoBoardCommentDto dto = new InfoBoardCommentDto(20001L, updated, 10001L);
        assertThrows(IllegalArgumentException.class, () -> commentService.update(dto));*/
    }

    @Test
    void update_empty_content() {
        String updated = "updated";
        InfoBoardCommentDto dto = new InfoBoardCommentDto(20001L, "", 10001L, null);
        assertThrows(IllegalArgumentException.class, () -> commentService.update(dto, "202011523"));
    }

    @Test
    void update_with_no_authorization() {
        InfoBoardCommentDto dto = new InfoBoardCommentDto(20001L, "updated",  null, null);
        assertThrows(IllegalStateException.class, () -> commentService.update(dto, "201713883"));
    }
}