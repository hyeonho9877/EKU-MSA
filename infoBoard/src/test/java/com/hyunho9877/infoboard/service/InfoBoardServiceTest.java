package com.hyunho9877.infoboard.service;

import com.hyunho9877.infoboard.domain.InfoBoard;
import com.hyunho9877.infoboard.dto.InfoBoardDto;
import com.hyunho9877.infoboard.repository.InfoBoardRepository;
import com.hyunho9877.infoboard.service.interfaces.InfoBoardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class InfoBoardServiceTest {

    @Autowired
    private InfoBoardService boardService;
    @Autowired
    private InfoBoardRepository boardRepository;

    @Test
    @DirtiesContext
    void apply_basic() {
        InfoBoardDto dto = new InfoBoardDto(null, "00010", "test apply", 0L);
        boardService.apply(dto, "201713883", "신현호");
        assertEquals(19, boardRepository.findAll().size());
    }

    @Test
    void apply_no_writer() {
        // INVALID CASE
        /*InfoBoardDto dto = new InfoBoardDto(null, "00010", "test apply", 0L);
        assertThrows(IllegalArgumentException.class, () -> boardService.apply(dto, null, "신현호"));*/
    }

    @Test
    void apply_no_building() {
        InfoBoardDto dto = new InfoBoardDto(null, null, "test apply", 0L);
        assertThrows(IllegalArgumentException.class, () -> boardService.apply(dto, "201713883", "신현호"));
    }

    @Test
    void apply_no_content() {
        InfoBoardDto dto = new InfoBoardDto(null, "00010", null, 0L);
        assertThrows(IllegalArgumentException.class, () -> boardService.apply(dto, "201713883", "신현호"));
    }

    @Test
    void apply_empty_writer() {
        // INVALID CASE
        /*InfoBoardDto dto = new InfoBoardDto(null, "00010", "test apply", 0L);
        assertThrows(IllegalArgumentException.class, () -> boardService.apply(dto, "", ""));*/
    }

    @Test
    void apply_empty_building() {
        InfoBoardDto dto = new InfoBoardDto(null, "", "test apply", 0L);
        assertThrows(IllegalArgumentException.class, () -> boardService.apply(dto, "201713883", "신현호"));
    }

    @Test
    void apply_empty_content() {
        InfoBoardDto dto = new InfoBoardDto(null, "00010", "", 0L);
        assertThrows(IllegalArgumentException.class, () -> boardService.apply(dto, "201713883", "신현호"));
    }

    @Test
    @DirtiesContext
    void delete_basic() {
        InfoBoardDto dto = new InfoBoardDto(10001L, "00010", null, 0L);
        boardService.delete(dto);
        assertFalse(boardRepository.findById(10001L).isPresent());
    }

    @Test
    void delete_no_id() {
        InfoBoardDto dto = new InfoBoardDto(null, "00010", null, 0L);
        assertThrows(InvalidDataAccessApiUsageException.class, () -> boardService.delete(dto));
    }

    @Test
    void delete_no_writer() {
        // INVALID CASE
        /*InfoBoardDto dto = new InfoBoardDto(10001L, "00010", null, 0L);
        assertThrows(IllegalArgumentException.class, () -> boardService.delete(dto));*/
    }

    @Test
    void delete_no_building() {
        InfoBoardDto dto = new InfoBoardDto(10001L, null, null, 0L);
        assertThrows(IllegalArgumentException.class, () -> boardService.delete(dto));
    }

    @Test
    void delete_empty_writer() {
        // INVALID CASE
        /*InfoBoardDto dto = new InfoBoardDto(10001L, "00010", null, 0L);
        assertThrows(IllegalArgumentException.class, () -> boardService.delete(dto));*/
    }

    @Test
    void delete_empty_building() {
        InfoBoardDto dto = new InfoBoardDto(10001L, "", null, 0L);
        assertThrows(IllegalArgumentException.class, () -> boardService.delete(dto));
    }

    @Test
    @DirtiesContext
    void update_basic() {
        String updated = "updated";
        InfoBoardDto dto = new InfoBoardDto(10001L, "00010", updated, 0L);
        boardService.update(dto);

        InfoBoard article = boardRepository.findById(10001L).orElseThrow();
        assertEquals(updated, article.getContent());
    }

    @Test
    void update_no_id() {
        String updated = "updated";
        InfoBoardDto dto = new InfoBoardDto(null, "00010", updated, 0L);
        assertThrows(InvalidDataAccessApiUsageException.class, () -> boardService.update(dto));
    }

    @Test
    void update_no_writer() {
        // INVALID CASE
        /*String updated = "updated";
        InfoBoardDto dto = new InfoBoardDto(10001L, "00010", updated, 0L);
        assertThrows(IllegalArgumentException.class, () -> boardService.update(dto));*/
    }

    @Test
    void update_no_building() {
        String updated = "updated";
        InfoBoardDto dto = new InfoBoardDto(10001L, null, updated, 0L);
        assertThrows(IllegalArgumentException.class, () -> boardService.update(dto));
    }

    @Test
    void update_no_content() {
        String updated = "updated";
        InfoBoardDto dto = new InfoBoardDto(10001L, "00010", null, 0L);
        assertThrows(IllegalArgumentException.class, () -> boardService.update(dto));
    }

    @Test
    void update_empty_writer() {
        // INVALID CASE
        /*String updated = "updated";
        InfoBoardDto dto = new InfoBoardDto(10001L, "00010", updated, 0L);
        assertThrows(IllegalArgumentException.class, () -> boardService.update(dto));*/
    }

    @Test
    void update_empty_building() {
        String updated = "updated";
        InfoBoardDto dto = new InfoBoardDto(10001L, "", updated, 0L);
        assertThrows(IllegalArgumentException.class, () -> boardService.update(dto));
    }

    @Test
    void update_empty_content() {
        String updated = "updated";
        InfoBoardDto dto = new InfoBoardDto(10001L, "00010", "", 0L);
        assertThrows(IllegalArgumentException.class, () -> boardService.update(dto));
    }

    @Test
    void recent_basic() {
        String building = "4";
        assertEquals(15, boardService.recent(building).size());
    }

    @Test
    void recent_no_building() {
        assertThrows(IllegalArgumentException.class, () -> boardService.recent(null));
    }

    @Test
    void recent_empty_building() {
        assertThrows(IllegalArgumentException.class, () -> boardService.recent(""));
    }
}