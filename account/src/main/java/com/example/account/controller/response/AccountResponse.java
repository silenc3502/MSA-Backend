package com.example.account.controller.response;

public record AccountResponse(
        Long accountId,
        String email,
        String nickname
) {}
