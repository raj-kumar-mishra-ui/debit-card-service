package com.example.cardservice.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Immutable audit record of an authorization attempt.
 */
@Entity
@Table(name = "card_transactions", uniqueConstraints = @UniqueConstraint(name = "uk_idempotency", columnNames = "idempotency_key"))
public class CardTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "idempotency_key", nullable = false, length = 80)
    private String idempotencyKey;
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;
    @Column(nullable = false, length = 3)
    private String currency;
    @Column(nullable = false, length = 120)
    private String merchant;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TransactionStatus status;
    @Column(nullable = false)
    private Instant createdAt;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "card_id", nullable = false)
    private DebitCard card;

    protected CardTransaction() {
    }

    public CardTransaction(String k, BigDecimal a, String c, String m, TransactionStatus s, Instant at, DebitCard card) {
        idempotencyKey = k;
        amount = a;
        currency = c;
        merchant = m;
        status = s;
        createdAt = at;
        this.card = card;
    }

    public Long getId() {
        return id;
    }

    public String getIdempotencyKey() {
        return idempotencyKey;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public String getMerchant() {
        return merchant;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
