package com.hyunho9877.critic.controller;

import com.hyunho9877.critic.domain.Critic;
import com.hyunho9877.critic.dto.CriticDTO;
import com.hyunho9877.critic.service.interfaces.CriticService;
import com.hyunho9877.critic.service.interfaces.KafkaService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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
    private final KafkaService kafkaService;

    @PostMapping("/apply")
    public ResponseEntity<CriticDTO> applyCritic(@RequestBody CriticDTO criticDTO) {
        String topic = "critic-lecture-topic";
        try {
            CriticDTO applied = criticService.apply(criticDTO);
            kafkaService.send(topic, applied);
            return ResponseEntity.ok(applied);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(criticDTO);
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<CriticDTO> deleteCritic(@RequestBody CriticDTO criticDTO) {
        try {
            return ResponseEntity.ok(criticService.delete(criticDTO));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(criticDTO);
        }
    }

    @PostMapping("/update")
    public ResponseEntity<CriticDTO> updateCritic(@RequestBody CriticDTO criticDTO) {
        try {
            return ResponseEntity.ok(criticService.update(criticDTO));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(criticDTO);
        } catch (NoSuchElementException e) {
            return ResponseEntity.internalServerError().body(criticDTO);
        }
    }

    @GetMapping("/recent")
    @CircuitBreaker(name = "critic-getRecentCircuitBreaker", fallbackMethod = "getFallback")
    public List<Critic> getRecent() {
        return criticService.getRecent();
    }

    public List<Critic> getFallback(Throwable t) {
        return Collections.emptyList();
    }

    @PostMapping("/get")
    @CircuitBreaker(name = "critic-getCriticsCircuitBreaker", fallbackMethod = "getCriticsFallback")
    public List<Critic> getCritics(@RequestBody CriticDTO criticDTO) {
        return criticService.get(criticDTO.getLectureName(), criticDTO.getLectureProfessor());
    }

    public List<Critic> getCriticsFallback(@RequestBody CriticDTO criticDTO, Throwable throwable) {
        return Collections.emptyList();
    }

}
