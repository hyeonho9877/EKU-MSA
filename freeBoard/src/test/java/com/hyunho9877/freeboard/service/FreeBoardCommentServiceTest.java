package com.hyunho9877.freeboard.service;

import com.hyunho9877.freeboard.domain.FreeBoardComment;
import com.hyunho9877.freeboard.dto.FreeBoardCommentDTO;
import com.hyunho9877.freeboard.repository.FreeBoardCommentRepository;
import com.hyunho9877.freeboard.service.interfaces.FreeBoardCommentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class FreeBoardCommentServiceTest {

    @Autowired
    private FreeBoardCommentService commentService;

    @Autowired
    private FreeBoardCommentRepository commentRepository;

    @Test
    @DirtiesContext
    void apply_basic() {
        FreeBoardCommentDTO dto = new FreeBoardCommentDTO(null, "201713883", "apply comment test", 10001L);
        commentService.apply(dto);
        assertEquals(5, commentRepository.findAll().size());
    }

    @Test
    @DirtiesContext
    void apply_no_writer() {
        // this case will be handled by @NumberFormat
        /*FreeBoardCommentDTO dto = new FreeBoardCommentDTO(null, null, "apply comment test", 10001L);
        assertThrows(Exception.class, () -> commentService.apply(dto));*/
    }

    @Test
    @DirtiesContext
    void apply_no_comment() {
        FreeBoardCommentDTO dto = new FreeBoardCommentDTO(null, "201713883", null, 10001L);
        assertThrows(IllegalArgumentException.class, () -> commentService.apply(dto));
    }

    @Test
    @DirtiesContext
    void apply_no_articleID() {
        FreeBoardCommentDTO dto = new FreeBoardCommentDTO(null, "201713883", "apply comment test", null);
        assertThrows(InvalidDataAccessApiUsageException.class, () -> commentService.apply(dto));
    }

    @Test
    @DirtiesContext
    void apply_empty_writer() {
        FreeBoardCommentDTO dto = new FreeBoardCommentDTO(null, "", "apply comment test", 10001L);
        assertThrows(IllegalArgumentException.class, () -> commentService.apply(dto));
    }

    @Test
    @DirtiesContext
    void apply_empty_content() {
        FreeBoardCommentDTO dto = new FreeBoardCommentDTO(null, "201713883", "", 10001L);
        assertThrows(IllegalArgumentException.class, () -> commentService.apply(dto));
    }

    @Test
    @DirtiesContext
    void delete_basic() {
        FreeBoardCommentDTO dto = new FreeBoardCommentDTO();
        dto.setId(20001L);
        dto.setArticleID(10001L);
        commentService.delete(dto);
        assertEquals(3, commentRepository.findAll().size());
    }

    @Test
    @DirtiesContext
    void delete_no_id() {
        FreeBoardCommentDTO dto = new FreeBoardCommentDTO();
        assertThrows(RuntimeException.class, () -> commentService.delete(dto));
    }

    @Test
    @DirtiesContext
    void delete_no_article_id() {
        FreeBoardCommentDTO dto = new FreeBoardCommentDTO();
        dto.setId(20001L);
        dto.setArticleID(19999L);
        assertThrows(NoSuchElementException.class, () -> commentService.delete(dto));
    }

    @Test
    @DirtiesContext
    void delete_non_exists_id() {
        FreeBoardCommentDTO dto = new FreeBoardCommentDTO();
        dto.setId(99999L);
        dto.setArticleID(10001L);
        assertThrows(EmptyResultDataAccessException.class, () -> commentService.delete(dto));
    }

    @Test
    @DirtiesContext
    void update_content() {
        FreeBoardCommentDTO dto = new FreeBoardCommentDTO(20001L, "201713883", "updated", 10001L);
        commentService.update(dto);
        FreeBoardComment comment = commentRepository.findById(20001L).orElseThrow();
        assertEquals(dto.getComment(), comment.getComment());
    }

    @Test
    @DirtiesContext
    void update_empty_content() {
        FreeBoardCommentDTO dto = new FreeBoardCommentDTO(20001L, "201713883", "", 10001L);
        assertThrows(IllegalArgumentException.class, () -> commentService.update(dto));
    }

    @Test
    @DirtiesContext
    void update_null_content() {
        FreeBoardCommentDTO dto = new FreeBoardCommentDTO(20001L, "201713883", null, 10001L);
        assertThrows(IllegalArgumentException.class, () -> commentService.update(dto));
    }

    @Test
    void get_recent() {
        List<FreeBoardComment> recent = commentService.recent(10004L);
        assertEquals(3, recent.size());
    }

    @Test
    void get_recent_null_id() {
        assertThrows(IllegalArgumentException.class, ()-> commentService.recent(null));
    }
}