package com.arthurh.portfolio.socialmedia.controller;

import static java.nio.file.Files.probeContentType;
import static java.nio.file.Paths.get;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import com.arthurh.portfolio.socialmedia.contract.controller.MediaController;
import com.arthurh.portfolio.socialmedia.contract.response.FileItem;
import com.arthurh.portfolio.socialmedia.service.MediaControllerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.jspecify.annotations.Nullable;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Log4j2
public class MediaControllerImpl implements MediaController {
    final MediaControllerService mediaControllerService;

    @Override
    public FileItem uploadFile(@Nullable String filename, @Nullable String contentType,
                               HttpServletRequest request) {
        contentType = extractContentType(filename, contentType, request);
        try (var inputStream = request.getInputStream()) {
            return mediaControllerService.upload(
                    filename, contentType,
                    request.getContentLengthLong(), inputStream);
        } catch (IOException e) {
            log.error("Failed uploading [type={} filename={}]: {}",
                    contentType, filename, e.getLocalizedMessage(), e);
            throw new UncheckedIOException(e);
        }
    }

    String extractContentType(@Nullable String filename, @Nullable String contentType,
                              HttpServletRequest request) {
        if (isNotBlank(contentType)) {
            return contentType;
        }
        if (isNotBlank(filename)) {
            try {
                return Optional.ofNullable(probeContentType(get(filename)))
                        .orElse(request.getContentType());
            } catch (IOException ignore) {
            }
        }
        return request.getContentType();
    }

    @Override
    public void downloadFile(String fileId, HttpServletResponse response) {
        var file = mediaControllerService.download(fileId);
        try (var stream = file.file()) {
            if (file.contentLength() > 0) {
                response.setContentLengthLong(file.contentLength());
            }
            response.setContentType(file.contentType());
            response.setHeader("Content-Disposition", "attachment; filename=\"" +
                    file.originalFileName() + "\"");
            try (var out = response.getOutputStream()) {
                stream.transferTo(out);
                out.flush();
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
