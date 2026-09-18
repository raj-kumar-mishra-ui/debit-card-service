package com.example.cardservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.math.BigDecimal;

/**
 * Type-safe limits read from application.yml.
 */
@ConfigurationProperties(prefix = "card.limits")
public record CardProperties(BigDecimal dailyDebit, BigDecimal singleTransaction) {
}
