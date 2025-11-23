package com.arthurh.portfolio.socialmedia.config;

import static java.util.UUID.randomUUID;
import static org.slf4j.MDC.put;
import static org.slf4j.MDC.remove;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@Order(Integer.MIN_VALUE)
public class LoggingFilter implements HandlerInterceptor {
    public final static String LOG_REQUEST_ID_NAME = "request.id";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        onStart();
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                Exception ex)
            throws Exception {
        onCompletion();
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

    void onStart() {
        put(LOG_REQUEST_ID_NAME, randomUUID().toString());
    }

    void onCompletion() {
        remove(LOG_REQUEST_ID_NAME);
    }
}
