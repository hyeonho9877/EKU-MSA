package com.hyunho9877.freeboard.controller;

import com.hyunho9877.freeboard.dto.FreeBoardDTO;
import com.hyunho9877.freeboard.service.interfaces.FreeBoardService;
import com.hyunho9877.freeboard.utils.common.JwtExtractor;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.Collections;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/board/free")
public class FreeBoardController {

    private final FreeBoardService boardService;
    private final JwtExtractor jwtExtractor;

    @PostMapping("/apply")
    public ResponseEntity<?> apply(@RequestBody @Valid FreeBoardDTO dto, Authentication authentication) {
        try {
            Jwt jwt = (Jwt) authentication.getPrincipal();
            return ResponseEntity.ok(boardService.apply(dto, jwtExtractor.getStudNo(jwt), jwtExtractor.getDepartment(jwt)));
        } catch (DataIntegrityViolationException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(dto);
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<?> delete(@RequestBody @Valid FreeBoardDTO dto, Authentication authentication) {
        try {
            Jwt jwt = (Jwt) authentication.getPrincipal();
            boardService.delete(dto, jwtExtractor.getStudNo(jwt));
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(dto);
        }
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody @Valid FreeBoardDTO dto, Authentication authentication) {
        try {
            Jwt jwt = (Jwt) authentication.getPrincipal();
            return ResponseEntity.ok(boardService.update(dto, jwtExtractor.getStudNo(jwt)));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(dto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.internalServerError().body(dto);
        }
    }

    @GetMapping("/recent")
    @CircuitBreaker(name = "freeBoardComment-recentCircuitBreaker", fallbackMethod = "commentRecentFallback")
    public ResponseEntity<?> recent(@RequestParam String building) {
        try {
            return ResponseEntity.ok(boardService.recent(building));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(building);
        }
    }

    public ResponseEntity<?> commentRecentFallback(String building, Throwable throwable) {
        return ResponseEntity.internalServerError().body(Collections.emptyList());
    }
}
