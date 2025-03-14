package com.task.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncLoggerService {

    private static final Logger logger = LoggerFactory.getLogger(AsyncLoggerService.class);

    @Async("loggerExecutor")  // Use the custom thread pool
    public void logMessage(String message) {
        logger.info("Logging in separate thread: {}", message);
    }
}

