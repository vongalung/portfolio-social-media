package com.arthurh.portfolio.socialmedia.config;

import static org.slf4j.MDC.*;

import org.springframework.core.task.TaskDecorator;

public class MdcTaskDecorator implements TaskDecorator {
    @Override
    public Runnable decorate(Runnable runnable) {
        var contextMap = getCopyOfContextMap();
        return () -> {
            try {
                setContextMap(contextMap);
                runnable.run();
            } finally {
                clear();
            }
        };
    }
}
