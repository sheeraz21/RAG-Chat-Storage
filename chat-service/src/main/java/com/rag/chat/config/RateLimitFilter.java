package com.rag.chat.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**

 * Developed by Syyed
 */

@Component
public class RateLimitFilter extends OncePerRequestFilter {

    private static final int LIMIT = 100;
    private static final long WINDOW_SECONDS = 60;

    private final ConcurrentHashMap<String, AtomicInteger> requestCounts = new ConcurrentHashMap<>();
    private volatile long windowStart = Instant.now().getEpochSecond();

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        long now = Instant.now().getEpochSecond();
        if (now - windowStart >= WINDOW_SECONDS) {
            requestCounts.clear();
            windowStart = now;
        }

        String key = request.getRemoteAddr();
        requestCounts.putIfAbsent(key, new AtomicInteger(0));

        if (requestCounts.get(key).incrementAndGet() > LIMIT) {
            response.setStatus(HttpServletResponse.SC_GATEWAY_TIMEOUT);
            response.getWriter().write("Too many requests");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
