package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/log")
public class LoggingController {

    private static final Logger logger = LoggerFactory.getLogger(LoggingController.class);

    @GetMapping
    public String logMessage() {
        logger.info("INFO level log message");
        logger.debug("DEBUG level log message");
        logger.error("ERROR level log message");
        return "Check your logs!";
    }
}
