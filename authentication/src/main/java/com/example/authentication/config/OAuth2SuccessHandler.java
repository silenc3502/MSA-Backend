package com.example.authentication.config;

import com.example.authentication.cache.service.RedisCacheService;
import com.example.authentication.client.AccountClient;
import com.example.authentication.service.UserTokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final UserTokenService userTokenService;
    private final AccountClient accountClient;
    private final OAuth2AuthorizedClientService authorizedClientService;
    private final RedisCacheService redisCacheService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        OAuth2User user = token.getPrincipal();

        String provider = token.getAuthorizedClientRegistrationId(); // "google"
        String oauthId = user.getAttribute("sub");
        String email = user.getAttribute("email");
        String name = user.getAttribute("name");
        String pictureUrl = user.getAttribute("picture");

        System.out.println("[OAuth2SuccessHandler] 로그인 성공");
        System.out.println(" > email: " + email);
        System.out.println(" > oauthId: " + oauthId);

        OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient(
                provider, token.getName()
        );
        String accessToken = authorizedClient.getAccessToken().getTokenValue();
        System.out.println("[OAuth2SuccessHandler] Google Access Token: " + accessToken);

        // ✅ Account 서비스에 요청
        String accountId = accountClient.getOrCreateAccountId(provider,
                oauthId,
                email,
                name,
                pictureUrl);
        System.out.println("[OAuth2SuccessHandler] 연동된 Account ID: " + accountId);

        // Redis: accountId → accessToken (3분 TTL)
        redisCacheService.setKeyAndValue(accountId, accessToken);

        // userToken 생성 시 accountId 포함
        // String userToken = userTokenService.generateAndStoreUserToken(accountId, email);
        String userToken = UUID.randomUUID().toString();
        redisCacheService.setKeyAndValue(userToken, accountId);

        System.out.println("[OAuth2SuccessHandler] 발급된 userToken: " + userToken);

        // 프론트엔드로 리디렉션
        String redirectUri = "http://localhost:3000/authentication/google/callback?userToken=" + userToken;
        response.sendRedirect(redirectUri);
    }
}
