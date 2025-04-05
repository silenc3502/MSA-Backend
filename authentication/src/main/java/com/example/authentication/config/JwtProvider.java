package com.example.authentication.config;

import com.example.authentication.entity.AuthenticatedUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {

    private final String secret = "your_jwt_secret_key"; // .env에서 로드 추천
    private final long accessTokenValidity = 1000 * 60 * 60; // 1시간
    private final long refreshTokenValidity = 1000 * 60 * 60 * 24 * 14; // 2주

    public String generateAccessToken(AuthenticatedUser user) {
        return generateToken(user, accessTokenValidity);
    }

    public String generateRefreshToken(AuthenticatedUser user) {
        return generateToken(user, refreshTokenValidity);
    }

    private String generateToken(AuthenticatedUser user, long validity) {
        return Jwts.builder()
                .setSubject(user.getOauthId())
                .claim("email", user.getEmail())
                .claim("provider", user.getProvider())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + validity))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }
}