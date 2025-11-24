package com.arthurh.portfolio.socialmedia.contract.controller;

public final class CustomRequestHeader {
    public static final String X_FILENAME_HEADER = "X-Filename";
    public static final String X_CONTENT_TYPE = "X-Content-Type";

    private CustomRequestHeader() {
        throw new AssertionError();
    }
}
