package com.task;

import com.task.util.AsyncLogger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class TaskApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskApplication.class, args);
		AsyncLogger asyncLogger = new AsyncLogger();
		asyncLogger.logDebug("testing the async logger in the main");
	}
}
