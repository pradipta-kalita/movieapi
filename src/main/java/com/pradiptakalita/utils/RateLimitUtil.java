package com.pradiptakalita.utils;

import com.pradiptakalita.auth.service.JwtService;
import com.pradiptakalita.exceptions.RateLimitExceptionForAuthenticatedUser;
import com.pradiptakalita.exceptions.RateLimitExceptionForUnauthenticatedUser;
import com.pradiptakalita.service.rateLimiter.RedisRateLimiterService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;

public class RateLimitUtil {

    private final RedisTemplate<String, Object> redisTemplate;

    public RateLimitUtil(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public boolean isRateLimitedForAuthenticatedUser(String email, String role, String endpoint) {
        int limit;
        Duration duration;

        switch (role) {
            case "USER":
                duration = Duration.ofMinutes(1);
                limit = 10;
                break;
            case "ADMIN":
                duration = Duration.ofMinutes(1);
                limit = 20;
                break;
            default:
                return true;
        }
        String key = "rateLimit::" + email + "::" + role + "::" + endpoint;
        Number cachedCountNumber = (Number) redisTemplate.opsForValue().get(key);
        Long cachedCount = (cachedCountNumber != null) ? cachedCountNumber.longValue() : 0L;
        if (cachedCount == 0) {
            redisTemplate.opsForValue().set(key, cachedCount, duration);
        }
        cachedCount = redisTemplate.opsForValue().increment(key);
        return cachedCount != null && cachedCount > limit;
    }

    public boolean isRateLimitedForUnauthenticatedUser(String ipAddress,String endpoint){
        final int limit = 20;
        String key = "rateLimit::"+ipAddress+"::"+endpoint;
        Number cachedCountNumber = (Number) redisTemplate.opsForValue().get(key);
        Long cachedCount = (cachedCountNumber!=null)?cachedCountNumber.longValue():0L;
        if(cachedCount == 0){
            redisTemplate.opsForValue().set(key,cachedCount,Duration.ofMinutes(30));
        }
        cachedCount = redisTemplate.opsForValue().increment(key);
        return cachedCount!=null && cachedCount>limit;
    }
}
