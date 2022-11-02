package com.hyunho9877.freeboard.controller;

import com.hyunho9877.freeboard.dto.FreeBoardCommentDTO;
import com.hyunho9877.freeboard.service.interfaces.FreeBoardCommentService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comment/free")
public class FreeBoardCommentController {

    private final FreeBoardCommentService commentService;

    @PostMapping("/apply")
    public ResponseEntity<?> apply(@RequestBody @Valid FreeBoardCommentDTO dto) {
        try {
            return ResponseEntity.ok(commentService.apply(dto));
        } catch (IllegalArgumentException | InvalidDataAccessApiUsageException e) {
            return ResponseEntity.badRequest().body(dto);
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<?> delete(@RequestBody @Valid FreeBoardCommentDTO dto) {
        try {
            commentService.delete(dto);
            return ResponseEntity.ok().build();
        } catch (InvalidDataAccessApiUsageException e) {
            return ResponseEntity.badRequest().body(dto);
        }
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody @Valid FreeBoardCommentDTO dto) {
        try {
            return ResponseEntity.ok(commentService.update(dto));
        } catch (InvalidDataAccessApiUsageException e) {
            return ResponseEntity.badRequest().body(dto);
        } catch (NoSuchElementException e) {
            return ResponseEntity.internalServerError().body(dto);
        }
    }

    @PostMapping("/recent")
    @CircuitBreaker(name = "freeBoard-recentCircuitBreaker", fallbackMethod = "boardRecentFallback")
    public ResponseEntity<?> recent(@RequestBody @Valid FreeBoardCommentDTO dto) {
        try {
            return ResponseEntity.ok(commentService.recent(dto.articleId()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(dto);
        }
    }

    public ResponseEntity<?> boardRecentFallback(@RequestBody @Valid FreeBoardCommentDTO dto, Throwable throwable) {
        return ResponseEntity.internalServerError().body(Collections.emptyList());
    }
}
