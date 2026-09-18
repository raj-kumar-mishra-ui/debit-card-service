package com.example.cardservice.dto;

import com.example.cardservice.entity.CardStatus;

import java.math.BigDecimal;

public record CardResponse(Long id, String maskedPan, CardStatus status, BigDecimal availableBalance) {
}
