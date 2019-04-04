package com.vita.collection.app.api.collection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppLogsWebApplication {
    private static final Logger logger = LogManager.getLogger(AppLogsWebApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(AppLogsWebApplication.class, args);

        logger.info("======");
    }

}
