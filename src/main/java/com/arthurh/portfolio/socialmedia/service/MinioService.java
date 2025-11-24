package com.arthurh.portfolio.socialmedia.service;

import static java.util.UUID.randomUUID;
import static org.apache.commons.io.FilenameUtils.getExtension;
import static org.apache.commons.lang3.StringUtils.isBlank;

import com.arthurh.portfolio.socialmedia.config.MinioProperties;
import com.arthurh.portfolio.socialmedia.service.dto.StreamedFile;
import io.minio.GetObjectArgs;
import io.minio.GetObjectResponse;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.MinioException;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.security.GeneralSecurityException;

@Service
@RequiredArgsConstructor
public class MinioService {
    private static final long UPLOAD_CHUCK_SIZE = 10 * 1024 * 1024;

    final MinioClient client;
    final MinioProperties properties;

    public String uploadFile(StreamedFile file)
            throws MinioException, IOException, GeneralSecurityException {
        var fileId = createFileName(file.originalFileName());
        var args = PutObjectArgs.builder()
                .bucket(properties.bucketName())
                .object(fileBucketPath(fileId))
                .stream(file.file(),
                        file.contentLength() > 0 ? file.contentLength() : -1,
                        UPLOAD_CHUCK_SIZE)
                .contentType(file.contentType())
                .build();

        client.putObject(args);
        return fileId;
    }

    String generateFileId() {
        return randomUUID().toString();
    }

    String createFileName(@Nullable String originalFileName) {
        var fileId = generateFileId();
        if (isBlank(originalFileName)) {
            return fileId;
        }
        return String.join(".", fileId, getExtension(originalFileName));
    }

    String fileBucketPath(String fileId) {
        if (properties.defaultDirectory() == null) {
            return fileId;
        }
        return String.join("/", properties.defaultDirectory(), fileId);
    }

    public GetObjectResponse downloadFile(String fileId) throws MinioException, IOException, GeneralSecurityException {
        return client.getObject(
                GetObjectArgs.builder()
                        .bucket(properties.bucketName())
                        .object(fileBucketPath(fileId))
                        .build());
    }

}
