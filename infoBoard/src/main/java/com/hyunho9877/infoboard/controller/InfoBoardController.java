package com.hyunho9877.infoboard.controller;

import com.hyunho9877.infoboard.domain.InfoBoard;
import com.hyunho9877.infoboard.dto.InfoBoardDto;
import com.hyunho9877.infoboard.service.interfaces.InfoBoardService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/board/info")
public class InfoBoardController {

    private final InfoBoardService boardService;

    @PostMapping("/apply")
    public ResponseEntity<InfoBoardDto> apply(@RequestBody InfoBoardDto dto) {
        try {
            boardService.apply(dto);
            return ResponseEntity.ok(dto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(dto);
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<InfoBoardDto> delete(@RequestBody InfoBoardDto dto) {
        try {
            boardService.delete(dto);
            return ResponseEntity.ok(dto);
        } catch (InvalidDataAccessApiUsageException e) {
            return ResponseEntity.badRequest().body(dto);
        }

    }

    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody InfoBoardDto dto) {
        try {
            boardService.update(dto);
            return ResponseEntity.ok(dto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(dto);
        }
    }

    @PostMapping("/recent")
    @CircuitBreaker(name = "infoboard-boardRecentCircuitBreaker", fallbackMethod = "boardRecentFallBack")
    public ResponseEntity<List<InfoBoard>> recent(@RequestBody InfoBoardDto dto) {
        try {
            return ResponseEntity.ok(boardService.recent(dto.getBuilding()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Collections.emptyList());
        }
    }

    public ResponseEntity<List<InfoBoard>> boardRecentFallBack(@RequestBody InfoBoardDto dto, Throwable throwable) {
        return ResponseEntity.internalServerError().body(Collections.emptyList());
    }

}
