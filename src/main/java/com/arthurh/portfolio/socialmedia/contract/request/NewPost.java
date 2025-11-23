package com.arthurh.portfolio.socialmedia.contract.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;

@JsonIgnoreProperties(ignoreUnknown = true)
public record NewPost(
        @NotBlank String title,
        String content
) { }
