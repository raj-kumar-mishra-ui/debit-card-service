package com.example.cardservice.dto;

import com.example.cardservice.entity.TransactionStatus;

import java.math.BigDecimal;

public record AuthorizationResponse(Long transactionId, TransactionStatus status, BigDecimal remainingBalance) {
}
