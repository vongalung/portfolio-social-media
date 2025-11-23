package com.arthurh.portfolio.socialmedia.config;

import org.jspecify.annotations.Nullable;
import java.time.ZoneId;
import java.util.Locale;

public record RequestHeaderWrapper(
        @Nullable String userId,
        @Nullable String username,
        @Nullable String userAgent,
        Locale locale,
        ZoneId timeZone
) { }
