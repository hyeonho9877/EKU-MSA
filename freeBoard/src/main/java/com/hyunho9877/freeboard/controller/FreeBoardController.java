package com.hyunho9877.freeboard.controller;

import com.hyunho9877.freeboard.dto.FreeBoardDTO;
import com.hyunho9877.freeboard.service.interfaces.FreeBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.Collections;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/board/free")
public class FreeBoardController {

    private final FreeBoardService boardService;

    @PostMapping("/apply")
    public ResponseEntity<?> apply(@RequestBody @Valid FreeBoardDTO dto) {
        try {
            return ResponseEntity.ok(boardService.apply(dto));
        } catch (DataIntegrityViolationException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(dto);
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<?> delete(@RequestBody @Valid FreeBoardDTO dto) {
        try {
            boardService.delete(dto);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(dto);
        }
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody @Valid FreeBoardDTO dto) {
        try {
            return ResponseEntity.ok(boardService.update(dto));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(dto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.internalServerError().body(dto);
        }
    }

    @PostMapping("/recent")
//    @CircuitBreaker(name = "freeBoardComment-recentCircuitBreaker", fallbackMethod = "commentRecentFallback")
    public ResponseEntity<?> recent(@RequestBody @Valid FreeBoardDTO dto) {
        try {
            return ResponseEntity.ok(boardService.recent(dto.getBuilding()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(dto);
        }
    }

    public ResponseEntity<?> commentRecentFallback(@RequestBody @Valid FreeBoardDTO dto, Throwable throwable) {
        return ResponseEntity.internalServerError().body(Collections.emptyList());
    }
}
