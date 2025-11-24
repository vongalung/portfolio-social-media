package com.arthurh.portfolio.socialmedia.service;

import static org.springframework.http.HttpHeaders.CONTENT_LENGTH;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.MediaType.APPLICATION_OCTET_STREAM_VALUE;

import com.arthurh.portfolio.socialmedia.contract.response.FileItem;
import com.arthurh.portfolio.socialmedia.service.dto.StreamedFile;
import io.minio.errors.MinioException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class MediaControllerService {
    final MinioService minioService;

    public FileItem upload(@Nullable String filename, String contentType,
                           long contentLength, InputStream file) {
        try {
            var fileId = minioService.uploadFile(new StreamedFile(
                    filename, contentType, contentLength, file));
            return new FileItem(fileId);
        } catch (MinioException | IOException | GeneralSecurityException e) {
            log.error("Failed uploading {} [filename={} size={}] to minio: {}",
                    contentType, filename, contentLength, e.getLocalizedMessage(), e);
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR, e.getLocalizedMessage(), e);
        }
    }

    public StreamedFile download(String fileId) {
        try {
            var stream = minioService.downloadFile(fileId);
            String contentType = Optional.ofNullable(stream.headers().get(CONTENT_TYPE))
                    .orElse(APPLICATION_OCTET_STREAM_VALUE);
            long contentLength = Long.parseLong(
                    Optional.ofNullable(stream.headers().get(CONTENT_LENGTH))
                            .orElse("-1"));

            return new StreamedFile(fileId, contentType, contentLength, stream);
        } catch (MinioException | IOException | GeneralSecurityException e) {
            log.error("Failed downloading {}: {}", fileId, e.getLocalizedMessage(), e);
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR, e.getLocalizedMessage(), e);
        }
    }
}
