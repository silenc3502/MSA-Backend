package com.example.account.controller.request;

public record AccountRequest(
        String provider,
        String providerId,
        String email,
        String name,
        String pictureUrl
) {}

