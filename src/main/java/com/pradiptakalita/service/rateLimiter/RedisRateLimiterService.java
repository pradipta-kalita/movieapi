package com.pradiptakalita.service.rateLimiter;

import com.pradiptakalita.auth.service.JwtService;
import com.pradiptakalita.exceptions.RateLimitExceptionForAuthenticatedUser;
import com.pradiptakalita.exceptions.RateLimitExceptionForUnauthenticatedUser;
import com.pradiptakalita.utils.RateLimitUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public class RedisRateLimiterService {

    private final JwtService jwtService;
    private final RateLimitUtil rateLimitUtil;

    public RedisRateLimiterService(JwtService jwtService, RateLimitUtil rateLimitUtil) {
        this.jwtService = jwtService;
        this.rateLimitUtil = rateLimitUtil;
    }

    public static String getClientIp(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("X-Real-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress;
    }

    public void isRateLimitedForAuthenticatedUser(HttpServletRequest request, String authHeader){
        String endpoint = request.getRequestURI();
        String jwt = authHeader.substring(7);
        String role = jwtService.extractRoles(jwt);
        String email = jwtService.extractUsername(jwt);

        if(rateLimitUtil.isRateLimitedForAuthenticatedUser(email,role,endpoint)){
            throw new RateLimitExceptionForAuthenticatedUser("Rate limit exceeded for a "+role);
        }
    }

    public void isRateLimitedForUnauthenticatedUser(HttpServletRequest request){
        String ipAddress = getClientIp(request);
        System.out.println("IP ADDRESS : "+ipAddress);
        String endpoint = request.getRequestURI();
        if(rateLimitUtil.isRateLimitedForUnauthenticatedUser(ipAddress,endpoint)){
            throw new RateLimitExceptionForUnauthenticatedUser("Rate limited exceeded. Try after some time");
        }
    }
}
