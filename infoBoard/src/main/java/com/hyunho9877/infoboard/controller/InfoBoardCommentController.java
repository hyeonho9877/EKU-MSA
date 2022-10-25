package com.hyunho9877.infoboard.controller;

import com.hyunho9877.infoboard.domain.InfoBoardComment;
import com.hyunho9877.infoboard.dto.InfoBoardCommentDto;
import com.hyunho9877.infoboard.service.interfaces.InfoBoardCommentService;
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
@RequestMapping("/api/v1/comment/info")
public class InfoBoardCommentController {

    private final InfoBoardCommentService commentService;

    @PostMapping("/apply")
    public ResponseEntity<InfoBoardCommentDto> apply(@RequestBody InfoBoardCommentDto dto) {
        try {
            commentService.apply(dto);
            return ResponseEntity.ok(dto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(dto);
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<InfoBoardCommentDto> delete(@RequestBody InfoBoardCommentDto dto) {
        try {
            commentService.delete(dto);
            return ResponseEntity.ok(dto);
        } catch (InvalidDataAccessApiUsageException e) {
            return ResponseEntity.badRequest().body(dto);
        }
    }

    @PostMapping("/update")
    public ResponseEntity<InfoBoardCommentDto> update(@RequestBody InfoBoardCommentDto dto) {
        try {
            commentService.update(dto);
            return ResponseEntity.ok(dto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(dto);
        }
    }

    @PostMapping("/recent")
    @CircuitBreaker(name = "infoboard-commentRecentCircuitBreaker", fallbackMethod = "commentRecentFallBack")
    public ResponseEntity<List<InfoBoardComment>> recent(@RequestBody InfoBoardCommentDto dto) {
        try {
            return ResponseEntity.ok(commentService.recent(dto.getArticle()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Collections.emptyList());
        }
    }

    public ResponseEntity<List<InfoBoardComment>> commentRecentFallBack(@RequestBody InfoBoardCommentDto dto, Throwable throwable) {
        return ResponseEntity.internalServerError().body(Collections.emptyList());
    }
}