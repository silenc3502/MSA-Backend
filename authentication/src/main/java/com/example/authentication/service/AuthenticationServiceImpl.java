package com.example.authentication.service;

import com.example.authentication.config.JwtProvider;
import com.example.authentication.entity.AuthenticatedUser;
import com.example.authentication.repository.AuthenticatedUserRepository;
import com.example.authentication.service.request.OAuthLoginRequest;
import com.example.authentication.service.response.OAuthUserInfo;
import com.example.authentication.service.response.TokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final JwtProvider jwtProvider;
    private final AuthenticatedUserRepository userRepository;

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String redirectUri;

    @Value("${spring.security.oauth2.client.provider.google.token-uri}")
    private String tokenUri;

    @Value("${spring.security.oauth2.client.provider.google.user-info-uri}")
    private String userInfoUri;

    public AuthenticationServiceImpl(JwtProvider jwtProvider,
                                     AuthenticatedUserRepository userRepository) {
        this.jwtProvider = jwtProvider;
        this.userRepository = userRepository;
    }

    @Override
    public TokenResponse authenticate(OAuthLoginRequest request) {
        String accessToken = getAccessTokenFromGoogle(request);
        OAuthUserInfo userInfo = getGoogleUserInfo(accessToken);

        AuthenticatedUser user = userRepository.findByOauthId(userInfo.getSub())
                .orElseGet(() -> userRepository.save(new AuthenticatedUser(userInfo.getSub(), userInfo.getEmail(), "google")));

        return new TokenResponse(
                jwtProvider.generateAccessToken(user),
                jwtProvider.generateRefreshToken(user)
        );
    }

    private String getAccessTokenFromGoogle(OAuthLoginRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        Map<String, String> params = new HashMap<>();
        params.put("code", request.getAuthorizationCode());
        params.put("client_id", clientId);
        params.put("client_secret", clientSecret);
        params.put("redirect_uri", request.getRedirectUri());
        params.put("grant_type", "authorization_code");

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(params, headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                tokenUri,
                HttpMethod.POST,
                entity,
                Map.class
        );

        return response.getBody().get("access_token").toString();
    }

    private OAuthUserInfo getGoogleUserInfo(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<OAuthUserInfo> response = restTemplate.exchange(
                userInfoUri,
                HttpMethod.GET,
                entity,
                OAuthUserInfo.class
        );

        return response.getBody();
    }
}
