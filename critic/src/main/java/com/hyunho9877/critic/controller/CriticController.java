package com.hyunho9877.critic.controller;

import com.hyunho9877.critic.domain.Critic;
import com.hyunho9877.critic.dto.CriticDTO;
import com.hyunho9877.critic.service.interfaces.CriticService;
import com.hyunho9877.critic.utils.common.JwtExtractor;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/critic")
public class CriticController {

    private final CriticService criticService;
    private final JwtExtractor jwtExtractor;

    @PostMapping("/apply")
    public ResponseEntity<CriticDTO> applyCritic(@RequestBody CriticDTO criticDTO, Authentication authentication) {
        try {
            Jwt jwt = (Jwt) authentication.getPrincipal();
            CriticDTO applied = criticService.apply(criticDTO, jwtExtractor.getStudNo(jwt), jwtExtractor.getDepartment(jwt));
            return ResponseEntity.ok(applied);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(criticDTO);
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<CriticDTO> deleteCritic(@RequestBody CriticDTO criticDTO, Authentication authentication) {
        try {
            Jwt jwt = (Jwt) authentication.getPrincipal();
            return ResponseEntity.ok(criticService.delete(criticDTO, jwtExtractor.getStudNo(jwt)));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(criticDTO);
        }
    }

    @PostMapping("/update")
    public ResponseEntity<CriticDTO> updateCritic(@RequestBody CriticDTO criticDTO, Authentication authentication) {
        try {
            Jwt jwt = (Jwt) authentication.getPrincipal();
            return ResponseEntity.ok(criticService.update(criticDTO, jwtExtractor.getStudNo(jwt)));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(criticDTO);
        } catch (NoSuchElementException e) {
            return ResponseEntity.internalServerError().body(criticDTO);
        }
    }

    @GetMapping("/recent")
    @CircuitBreaker(name = "critic-getRecentCircuitBreaker", fallbackMethod = "getFallback")
    public ResponseEntity<List<CriticDTO>> getRecent(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(criticService.getRecent(pageable));
    }

    public ResponseEntity<List<CriticDTO>> getFallback(Throwable t) {
        return ResponseEntity.internalServerError().body(Collections.emptyList());
    }

    @PostMapping("/get")
    @CircuitBreaker(name = "critic-getCriticsCircuitBreaker", fallbackMethod = "getCriticsFallback")
    public ResponseEntity<List<CriticDTO>> getCritics(@RequestBody CriticDTO criticDTO, @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(criticService.get(criticDTO.lectureName(), criticDTO.lectureProfessor(), pageable));
    }

    public ResponseEntity<List<Critic>> getCriticsFallback(@RequestBody CriticDTO criticDTO, Pageable pageable, Throwable throwable) {
        return ResponseEntity.internalServerError().body(Collections.emptyList());
    }

}
