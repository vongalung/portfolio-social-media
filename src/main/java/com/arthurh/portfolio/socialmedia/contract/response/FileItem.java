package com.arthurh.portfolio.socialmedia.contract.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record FileItem(
        String fileId
) { }
