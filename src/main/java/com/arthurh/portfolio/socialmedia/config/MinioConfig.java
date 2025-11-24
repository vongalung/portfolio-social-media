package com.arthurh.portfolio.socialmedia.config;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class MinioConfig {
    final MinioProperties properties;

    @Bean
    public MinioClient minioClient() {
        var builder = MinioClient.builder()
                .endpoint(properties.endpoint());
        if (isNotBlank(properties.accessKey()) && isNotBlank(properties.secretKey())) {
            builder.credentials(properties.accessKey(), properties.secretKey());
        }
        return builder.build();
    }
}
