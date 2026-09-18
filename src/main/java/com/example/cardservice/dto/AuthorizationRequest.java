package com.example.cardservice.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

/**
 * API request for a debit authorization.
 */
public record AuthorizationRequest(@NotBlank @Size(max = 80) String idempotencyKey,
                                   @NotNull @DecimalMin("0.01") BigDecimal amount,
                                   @NotBlank @Pattern(regexp = "[A-Z]{3}") String currency,
                                   @NotBlank @Size(max = 120) String merchant) {
}
