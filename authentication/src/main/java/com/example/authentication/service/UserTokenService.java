package com.example.authentication.service;

public interface UserTokenService {
    String generateAndStoreUserToken(String oauthId, String email);
    String getOauthIdByUserToken(String userToken);
}
