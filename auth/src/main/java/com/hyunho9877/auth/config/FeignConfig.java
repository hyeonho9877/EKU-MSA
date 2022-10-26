package com.hyunho9877.auth.config;

import com.hyunho9877.auth.feign.OAuthClientCredentialsFeignManager;
import feign.RequestInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

@Configuration
@RequiredArgsConstructor
public class FeignConfig {

    public static final String CLIENT_REGISTRATION_ID = "keycloak";

    private final OAuth2AuthorizedClientService clientService;
    private final ClientRegistrationRepository clientRegistrationRepository;

    @Bean
    public RequestInterceptor requestInterceptor() {
        ClientRegistration clientRegistration = clientRegistrationRepository.findByRegistrationId(CLIENT_REGISTRATION_ID);
        OAuthClientCredentialsFeignManager feignManager = new OAuthClientCredentialsFeignManager(authorizedManager(), clientRegistration);
        return requestTemplate -> {
            requestTemplate.header("Authorization", "Bearer " + feignManager.getAccessToken());
        };

    }

    OAuth2AuthorizedClientManager authorizedManager() {
        OAuth2AuthorizedClientProvider clientProvider = OAuth2AuthorizedClientProviderBuilder.builder()
                .clientCredentials()
                .build();

        AuthorizedClientServiceOAuth2AuthorizedClientManager clientManager = new AuthorizedClientServiceOAuth2AuthorizedClientManager(clientRegistrationRepository, clientService);
        clientManager.setAuthorizedClientProvider(clientProvider);
        return clientManager;
    }
}
