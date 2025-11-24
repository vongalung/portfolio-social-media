package com.arthurh.portfolio.socialmedia.contract.controller;

import static com.arthurh.portfolio.socialmedia.contract.controller.CustomRequestHeader.X_CONTENT_TYPE;
import static com.arthurh.portfolio.socialmedia.contract.controller.CustomRequestHeader.X_FILENAME_HEADER;
import static org.springframework.http.MediaType.APPLICATION_OCTET_STREAM_VALUE;

import com.arthurh.portfolio.socialmedia.contract.response.FileItem;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/media")
public interface MediaController {
    @PostMapping(value = "/file", consumes = APPLICATION_OCTET_STREAM_VALUE)
    FileItem uploadFile(@RequestHeader(value = X_FILENAME_HEADER, required = false) String filename,
                        @RequestHeader(value = X_CONTENT_TYPE, required = false) String contentType,
                        HttpServletRequest request);

    @GetMapping("/file/{fileId}")
    void downloadFile(@PathVariable String fileId, HttpServletResponse response);
}
