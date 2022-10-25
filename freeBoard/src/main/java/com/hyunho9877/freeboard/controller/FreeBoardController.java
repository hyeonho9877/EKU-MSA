package com.hyunho9877.freeboard.controller;

import com.hyunho9877.freeboard.dto.FreeBoardDTO;
import com.hyunho9877.freeboard.service.interfaces.FreeBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;

@Slf4j
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

    @GetMapping("/recent")
//    @CircuitBreaker(name = "freeBoardComment-recentCircuitBreaker", fallbackMethod = "commentRecentFallback")
    public ResponseEntity<?> recent(@RequestParam String building) {
        try {
            return ResponseEntity.ok(boardService.recent(building));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(building);
        }
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    public ResponseEntity<?> commentRecentFallback(@RequestBody @Valid FreeBoardDTO dto, Throwable throwable) {
        return ResponseEntity.internalServerError().body(Collections.emptyList());
    }

    @GetMapping("/callback")
    public void oauthCallBack(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        Iterator<String> headerIterator = headerNames.asIterator();
        while (headerIterator.hasNext()) {
            String header = headerIterator.next();
            String headerContent = request.getHeader(header);
            log.info("{} : {}", header, headerContent);
        }
    }
}
