package com.arthurh.portfolio.socialmedia.contract.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record NewReply(
        String content
) { }
