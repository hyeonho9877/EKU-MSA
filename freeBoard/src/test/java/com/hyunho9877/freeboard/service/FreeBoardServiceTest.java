package com.hyunho9877.freeboard.service;

import com.hyunho9877.freeboard.domain.FreeBoard;
import com.hyunho9877.freeboard.dto.FreeBoardDTO;
import com.hyunho9877.freeboard.repository.FreeBoardRepository;
import com.hyunho9877.freeboard.service.interfaces.FreeBoardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class FreeBoardServiceTest {

    @Autowired
    private FreeBoardService freeBoardService;

    @Autowired
    private FreeBoardRepository boardRepository;

    @Test
    @DirtiesContext
    void apply_basic() {
        FreeBoardDTO dto = new FreeBoardDTO(null, "201713883", "apply test", null, "00010");
        freeBoardService.apply(dto);
        assertEquals(5, boardRepository.findAll().size());
    }

    @Test
    @DirtiesContext
    void apply_no_writer() {
        // this case will be handled by @NumberFormat
        /*FreeBoardDTO dto = new FreeBoardDTO(null, null, "apply test", null, "00010");
        assertThrows(DataIntegrityViolationException.class, () -> freeBoardService.apply(dto));*/
    }

    @Test
    @DirtiesContext
    void apply_no_content() {
        FreeBoardDTO dto = new FreeBoardDTO(null, "20713883", null, null, "00010");
        assertThrows(IllegalArgumentException.class, () -> freeBoardService.apply(dto));
    }

    @Test
    @DirtiesContext
    void apply_no_building() {
        // this case will be handled by @NumberFormat
        /*FreeBoardDTO dto = new FreeBoardDTO(null, "20713883", "apply test", null, null);
        assertThrows(DataIntegrityViolationException.class, () -> freeBoardService.apply(dto));*/
    }

    @Test
    void apply_empty_writer() {
        FreeBoardDTO dto = new FreeBoardDTO(null, "", "apply test", null, "00010");
        assertThrows(IllegalArgumentException.class, () -> freeBoardService.apply(dto));
    }

    @Test
    void apply_empty_content() {
        FreeBoardDTO dto = new FreeBoardDTO(null, "201713883", "", null, "00010");
        assertThrows(IllegalArgumentException.class, () -> freeBoardService.apply(dto));
    }

    @Test
    void apply_empty_building() {
        FreeBoardDTO dto = new FreeBoardDTO(null, "201713883", "apply test", null, "");
        assertThrows(IllegalArgumentException.class, () -> freeBoardService.apply(dto));
    }

    @Test
    @DirtiesContext
    void delete_basic() {
        FreeBoardDTO freeBoardDTO = new FreeBoardDTO();
        freeBoardDTO.setId(10001L);
        freeBoardService.delete(freeBoardDTO);
        assertEquals(3, boardRepository.findAll().size());
    }

    @Test
    @DirtiesContext
    void delete_no_id() {
        FreeBoardDTO freeBoardDTO = new FreeBoardDTO();
        assertThrows(RuntimeException.class, () -> freeBoardService.delete(freeBoardDTO));
    }

    @Test
    @DirtiesContext
    void delete_non_exists_id() {
        FreeBoardDTO freeBoardDTO = new FreeBoardDTO();
        freeBoardDTO.setId(99999L);
    }

    @Test
    @DirtiesContext
    void update_content() {
        FreeBoardDTO dto = new FreeBoardDTO(10001L, "201713883", "updated", 0, "00010");
        freeBoardService.update(dto);
        FreeBoard freeBoard = boardRepository.findById(10001L).orElseThrow();
        assertEquals(dto.getContent(), freeBoard.getContent());
    }

    @Test
    void update_empty_content() {
        FreeBoardDTO dto = new FreeBoardDTO(10001L, "201713883", "", 0, "00010");
        assertThrows(IllegalArgumentException.class, () -> freeBoardService.update(dto));
    }

    @Test
    void update_null_content() {
        FreeBoardDTO dto = new FreeBoardDTO(10001L, "201713883", null, 0, "00010");
        assertThrows(IllegalArgumentException.class, () -> freeBoardService.update(dto));
    }
}