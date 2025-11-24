package com.arthurh.portfolio.socialmedia.config;

import jakarta.validation.constraints.NotBlank;
import org.jspecify.annotations.Nullable;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "minio")
public record MinioProperties(
        @NotBlank String endpoint,
        @Nullable String accessKey,
        @Nullable String secretKey,
        @NotBlank String bucketName,
        @Nullable String defaultDirectory
) { }
