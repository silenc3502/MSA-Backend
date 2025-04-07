package com.example.authentication.service;

import com.example.authentication.service.UserTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserTokenServiceImpl implements UserTokenService {

    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public String generateAndStoreUserToken(String accountId, String email) {
        String userToken = UUID.randomUUID().toString();

        redisTemplate.opsForValue().set(userToken, accountId, Duration.ofHours(1));

        System.out.println("[UserTokenServiceImpl] Redis 저장 완료");
        System.out.println(" > key: " + userToken);
        System.out.println(" > value(oauthId): " + accountId);

        return userToken;
    }

    @Override
    public String getOauthIdByUserToken(String userToken) {
        String key = "auth:userToken:" + userToken;
        String value = redisTemplate.opsForValue().get(key);

        System.out.println("[UserTokenServiceImpl] Redis 조회");
        System.out.println(" > key: " + key);
        System.out.println(" > value: " + value);

        return value;
    }
}
