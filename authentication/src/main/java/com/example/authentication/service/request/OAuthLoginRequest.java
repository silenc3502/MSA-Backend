package com.example.authentication.service.request;

public class OAuthLoginRequest {
    private String authorizationCode;
    private String redirectUri;
    private String provider; // 예: "google", "kakao" 등

    public OAuthLoginRequest() {}

    public OAuthLoginRequest(String authorizationCode, String redirectUri, String provider) {
        this.authorizationCode = authorizationCode;
        this.redirectUri = redirectUri;
        this.provider = provider;
    }

    public String getAuthorizationCode() {
        return authorizationCode;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public String getProvider() {
        return provider;
    }
}
