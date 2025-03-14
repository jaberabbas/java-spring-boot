package com.task.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncLogger {

    private static final Logger logger = LoggerFactory.getLogger(AsyncLogger.class);

    @Async("loggerExecutor")
    public void logError(String message, Throwable e) {
        logger.error(message, e);
    }

    @Async("loggerExecutor")
    public void logInfo(String message, Object o) {
        logger.info("{} - {}", message, o.toString());
    }

    @Async("loggerExecutor")
    public void logDebug(String message, Object o) {
        logger.info("{} - {}", message, o.toString());
    }
}

