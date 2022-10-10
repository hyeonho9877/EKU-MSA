package com.hyunho9877.doodle.controller;

import com.hyunho9877.doodle.domain.Doodle;
import com.hyunho9877.doodle.dto.DoodleDTO;
import com.hyunho9877.doodle.service.interfaces.DoodleService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/doodle")
public class DoodleController {

    private final DoodleService doodleService;

    @PostMapping("/apply")
    public ResponseEntity<?> apply(@RequestBody DoodleDTO doodleDTO) {
        try {
            return ResponseEntity.ok(doodleService.apply(doodleDTO));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<DoodleDTO> delete(@RequestBody DoodleDTO doodleDTO) {
        try {
            return ResponseEntity.ok(doodleService.delete(doodleDTO));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(doodleDTO);
        }
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody DoodleDTO doodleDTO) {
        try {
            doodleService.update(doodleDTO.getDoodleId(), doodleDTO.getContent());
            return ResponseEntity.ok(doodleDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (NoSuchElementException e) {
            return ResponseEntity.internalServerError().body(doodleDTO);
        }
    }

    @GetMapping("/recent")
    @CircuitBreaker(name = "doodle-recentCircuitBreaker", fallbackMethod = "recentFallback")
    public List<Doodle> recent(@RequestParam String building, @PageableDefault(size = 10, sort = "doodleId", direction = Sort.Direction.DESC) Pageable pageable) {
        return doodleService.get(building, pageable);
    }

    public List<Doodle> recentFallback(@RequestParam String building, @PageableDefault(size = 10, sort = "doodleId", direction = Sort.Direction.DESC) Pageable pageable, Throwable t) {
        return Collections.emptyList();
    }
}
