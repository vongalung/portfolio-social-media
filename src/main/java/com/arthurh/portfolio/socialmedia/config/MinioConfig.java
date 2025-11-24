package com.arthurh.portfolio.socialmedia.config;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

import io.minio.MinioClient;
import jakarta.validation.constraints.NotBlank;
import org.jspecify.annotations.Nullable;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "minio")
public record MinioConfig(
        @NotBlank String endpoint,
        @Nullable String accessKey,
        @Nullable String secretKey,
        @NotBlank String bucketName
) {
    @Bean
    public MinioClient minioClient() {
        var builder = MinioClient.builder()
                .endpoint(endpoint);
        if (isNotBlank(accessKey) && isNotBlank(secretKey)) {
            builder.credentials(accessKey, secretKey);
        }
        return builder.build();
    }
}
