package com.hyunho9877.auth.feign;

import com.hyunho9877.auth.config.FeignConfig;
import com.hyunho9877.auth.dto.KeyCloakUserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "keycloak-feign-client", url = "http://keycloak/", configuration = FeignConfig.class)
public interface KeyCloakFeignClient {
    @PostMapping(value = "/admin/realms/eku/users", consumes = MediaType.APPLICATION_JSON_VALUE)
    void registerUser(@RequestBody KeyCloakUserDto userRepresentation);

    @DeleteMapping(value = "admin/realms/eku/users/{id}")
    void withdraw(@PathVariable String id);
}
