package com.example.authentication.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class AccountClient {

    private final WebClient webClient;

    @Value("${account.service.url}")
    private String accountServiceUrl;

    public String getOrCreateAccountId(String provider, String providerId, String email, String name, String pictureUrl) {
        return webClient.post()
                .uri(accountServiceUrl + "/accounts")
                .bodyValue(new AccountRequest(provider, providerId, email, name, pictureUrl))
                .retrieve()
                .bodyToMono(AccountResponse.class)
                .map(AccountResponse::accountId)
                .block(); // 동기 처리
    }

    public record AccountRequest(
            String provider,
            String providerId,
            String email,
            String name,
            String pictureUrl
    ) {}

    public record AccountResponse(
            String accountId,
            String email,
            String nickname
    ) {}
}
