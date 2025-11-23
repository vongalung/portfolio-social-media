package com.arthurh.portfolio.socialmedia.config;

import static org.springframework.context.i18n.LocaleContextHolder.getLocale;
import static org.springframework.context.i18n.LocaleContextHolder.getTimeZone;
import static org.springframework.http.HttpHeaders.USER_AGENT;
import static org.springframework.security.core.context.SecurityContextHolder.getContext;
import static org.springframework.web.context.request.RequestContextHolder.getRequestAttributes;

import org.jspecify.annotations.Nullable;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.context.request.ServletRequestAttributes;
import java.util.Optional;

public class RequestHeaderContext {
    private static final ThreadLocal<@Nullable RequestHeaderWrapper> REQUEST_HEADER = new ThreadLocal<>();

    public static Optional<RequestHeaderWrapper> getHeader() {
        if (REQUEST_HEADER.get() == null) {
            setHeader(extractFromRequest());
        }
        return Optional.ofNullable(REQUEST_HEADER.get());
    }

    private static void setHeader(RequestHeaderWrapper header) {
        REQUEST_HEADER.set(header);
    }

    public static void clear() {
        REQUEST_HEADER.remove();
    }

    static RequestHeaderWrapper extractFromRequest() {
        return new RequestHeaderWrapper(
                extractUserId(),
                extractUsername(),
                extractUserAgent(),
                getLocale(),
                getTimeZone().toZoneId());
    }

    @Nullable
    static String extractUserId() {
        var auth = getContext().getAuthentication();
        var principle = auth == null ? null : auth.getPrincipal();
        if (principle instanceof Jwt jwt) {
            return jwt.getSubject();
        }
        return null;
    }

    @Nullable
    static String extractUsername() {
        var auth = getContext().getAuthentication();
        var principle = auth == null ? null : auth.getPrincipal();
        if (principle instanceof Jwt jwt) {
            return jwt.getClaimAsString("preferred_username");
        }
        return null;
    }

    @Nullable
    static String extractUserAgent() {
        if (getRequestAttributes() instanceof ServletRequestAttributes attributes) {
            return attributes.getRequest().getHeader(USER_AGENT);
        }
        return null;
    }
}
