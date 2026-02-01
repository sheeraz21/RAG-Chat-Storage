package com.rag.chat.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**

 * Developed by Syyed
 */

@Component
public class RateLimitInterceptor implements HandlerInterceptor {

    @Value("${rate-limit.max}")
    private int maxRequests;

    private final Map<String, AtomicInteger> requests = new ConcurrentHashMap<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String client = request.getRemoteAddr();
        requests.putIfAbsent(client, new AtomicInteger(maxRequests));
        int remaining = requests.get(client).decrementAndGet();
        response.setHeader("X-Rate-Limit-Remaining", String.valueOf(Math.max(remaining, 0)));
        return remaining >= 0;
    }
}
