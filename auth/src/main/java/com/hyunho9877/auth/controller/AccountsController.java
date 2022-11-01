package com.hyunho9877.auth.controller;

import com.hyunho9877.auth.dto.AccountDto;
import com.hyunho9877.auth.enums.KafkaTopics;
import com.hyunho9877.auth.service.interfaces.AccountService;
import com.hyunho9877.auth.service.interfaces.KafkaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.EnumSet;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts")
public class AccountsController {

    private final AccountService accountService;
    private final KafkaService kafkaService;


    @PostMapping("/registration")
    public ResponseEntity<AccountDto> registration(@RequestBody AccountDto dto) {
        accountService.register(dto);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<?> withdraw(Authentication authentication) {
        String userId = authentication.getName();
        Jwt principal = (Jwt) authentication.getPrincipal();
        String username = principal.getClaimAsString("preferred_username");
        accountService.withdraw(userId);
        kafkaService.send(username, EnumSet.allOf(KafkaTopics.class));
        return ResponseEntity.ok(userId);
    }
}
