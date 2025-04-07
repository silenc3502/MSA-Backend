package com.example.authentication.service;

public interface UserTokenService {
    String generateAndStoreUserToken(String accountId, String email);
    String getOauthIdByUserToken(String userToken);
}
