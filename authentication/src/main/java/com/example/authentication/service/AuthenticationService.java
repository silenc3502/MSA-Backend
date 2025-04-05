package com.example.authentication.service;

import com.example.authentication.service.request.OAuthLoginRequest;
import com.example.authentication.service.response.TokenResponse;

public interface AuthenticationService {
    TokenResponse authenticate(OAuthLoginRequest request);
}

