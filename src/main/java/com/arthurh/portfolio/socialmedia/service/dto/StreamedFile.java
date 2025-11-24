package com.arthurh.portfolio.socialmedia.service.dto;

import org.jspecify.annotations.Nullable;
import java.io.InputStream;

public record StreamedFile (
        @Nullable String originalFileName,
        String contentType,
        long contentLength,
        InputStream file
) { }
