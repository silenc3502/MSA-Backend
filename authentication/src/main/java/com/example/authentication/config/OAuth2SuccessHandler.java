package com.example.authentication.config;

import com.example.authentication.service.UserTokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final UserTokenService userTokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        OAuth2User user = token.getPrincipal();

        String email = user.getAttribute("email");
        String oauthId = user.getAttribute("sub");

        System.out.println("[OAuth2SuccessHandler] 로그인 성공");
        System.out.println(" > email: " + email);
        System.out.println(" > oauthId: " + oauthId);

        String userToken = userTokenService.generateAndStoreUserToken(oauthId, email);

        System.out.println("[OAuth2SuccessHandler] 발급된 userToken: " + userToken);

        String redirectUri = "http://localhost:3000/oauth/callback?userToken=" + userToken;
        System.out.println("[OAuth2SuccessHandler] Redirect URI: " + redirectUri);

        response.sendRedirect(redirectUri);
    }
}
