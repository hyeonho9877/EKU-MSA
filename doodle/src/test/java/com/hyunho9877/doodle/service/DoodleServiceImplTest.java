package com.hyunho9877.doodle.service;

import com.hyunho9877.doodle.domain.Doodle;
import com.hyunho9877.doodle.dto.DoodleDTO;
import com.hyunho9877.doodle.repository.DoodleRepository;
import com.hyunho9877.doodle.service.interfaces.DoodleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

@Transactional
@SpringBootTest
class DoodleServiceImplTest {

    @Autowired
    DoodleService doodleService;

    @Autowired
    DoodleRepository doodleRepository;

    @BeforeEach
    @Transactional
    void insert() {
        doodleRepository.save(new Doodle(null, "hello", "0001"));
    }

    @Test
    @DisplayName("정상 입력")
    void apply_t1() {
        DoodleDTO doodleDTO = new DoodleDTO();
        doodleDTO.setBuilding("0001");
        doodleDTO.setContent("hello");

        DoodleDTO apply = doodleService.apply(doodleDTO);
        assertThat(apply.getBuilding()).isEqualTo(doodleDTO.getBuilding());
        assertThat(apply.getContent()).isEqualTo(doodleDTO.getContent());
    }

    @Test
    @DisplayName("내용 누락")
    void apply_t2() {
        try {
            DoodleDTO doodleDTO = new DoodleDTO();
            doodleDTO.setBuilding("0001");
            doodleService.apply(doodleDTO);
            fail();
        } catch (IllegalArgumentException e) {

        }
    }

    @Test
    @DisplayName("빌딩 정보 누락")
    void apply_t3() {
        try {
            DoodleDTO doodleDTO = new DoodleDTO();
            doodleDTO.setContent("hello");
            doodleService.apply(doodleDTO);
            fail();
        } catch (DataIntegrityViolationException e) {

        }
    }

    @Test
    @DisplayName("내용, 빌딩 정보 누락")
    void apply_t4() {
        try {
            DoodleDTO doodleDTO = new DoodleDTO();
            doodleService.apply(doodleDTO);
            fail();
        } catch (IllegalArgumentException e) {

        }
    }


    @Test
    @DisplayName("정상 입력")
    void delete_t1() {
        Doodle doodle = doodleRepository.findAll().get(0);
        DoodleDTO doodleDTO = new DoodleDTO(doodle.getDoodleId(), "hello", "0001");
        DoodleDTO delete = doodleService.delete(doodleDTO);
        assertThat(delete.getDoodleId()).isEqualTo(doodleDTO.getDoodleId());
    }

    @Test
    @DisplayName("존재하지 않는 id")
    void delete_t2() {
        try {
            DoodleDTO doodleDTO = new DoodleDTO(100L);
            doodleService.delete(doodleDTO);
            fail();
        } catch (EmptyResultDataAccessException e) {

        }
    }

    @Test
    @DisplayName("아이디 누락")
    void delete_t3() {
        try {
            DoodleDTO doodleDTO = new DoodleDTO();
            doodleService.delete(doodleDTO);
            fail();
        } catch (IllegalArgumentException e) {

        }
    }

    @Test
    @DisplayName("정상 입력")
    void update() {
        Doodle doodle = doodleRepository.findAll().get(0);
        Doodle copy = new Doodle(doodle.getDoodleId(), doodle.getContent(), doodle.getBuilding());

        doodleService.update(doodle.getDoodleId(), "change");

        assertThat(copy.getContent()).isNotEqualTo(doodle.getContent());
    }

    @Test
    void get() {
    }
}