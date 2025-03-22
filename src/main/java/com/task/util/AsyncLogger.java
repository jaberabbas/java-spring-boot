package com.task.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncLogger {

    private static final Logger logger = LoggerFactory.getLogger(AsyncLogger.class);

    @Async("loggerExecutor")
    public void logError(String message, Object ... o) {
        logger.error(message, o);
    }

    @Async("loggerExecutor")
    public void logInfo(String message, Object ... o) {
        logger.info(message, o);
    }

    @Async("loggerExecutor")
    public void logDebug(String message, Object ... o) {
        logger.debug(message, o);
    }
}

