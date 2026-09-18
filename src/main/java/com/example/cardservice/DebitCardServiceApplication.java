package com.example.cardservice;

import com.example.cardservice.config.CardProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Entry point for the debit-card microservice.
 */
@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties(CardProperties.class)
public class DebitCardServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(DebitCardServiceApplication.class, args);
    }
}
