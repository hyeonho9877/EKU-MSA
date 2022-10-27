package com.hyunho9877.auth.service;

import com.hyunho9877.auth.dto.AccountDto;
import com.hyunho9877.auth.dto.KeyCloakUserDto;
import com.hyunho9877.auth.feign.KeyCloakFeignClient;
import com.hyunho9877.auth.service.interfaces.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final KeyCloakFeignClient feignClient;

    @Override
    public void register(AccountDto dto) {
        KeyCloakUserDto userRepresentation = KeyCloakUserDto.builder()
                .username(dto.getUserId())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .enabled(true)
                .credentials(List.of(new KeyCloakUserDto.CredentialsDto(dto.getPassword(), false)))
                .build();

        feignClient.registerUser(userRepresentation);
    }
}
