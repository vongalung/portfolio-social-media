package com.arthurh.portfolio.socialmedia.contract.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record SimpleUserContent(
        UUID id,
        String username,
        ZonedDateTime timestamp,
        String title,
        String content,
        long replies,
        Map<String, Long> reactions
) { }
