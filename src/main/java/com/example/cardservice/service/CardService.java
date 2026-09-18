package com.example.cardservice.service;

import com.example.cardservice.config.CardProperties;
import com.example.cardservice.dto.*;
import com.example.cardservice.entity.*;
import com.example.cardservice.exception.*;
import com.example.cardservice.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;

/**
 * Transactional business service for card lifecycle and debit authorization.
 */
@Service
public class CardService {
    private final DebitCardRepository cards;
    private final CardTransactionRepository txns;
    private final CardProperties limits;
    private final Clock clock;

    public CardService(DebitCardRepository cards, CardTransactionRepository txns, CardProperties limits, Clock clock) {
        this.cards = cards;
        this.txns = txns;
        this.limits = limits;
        this.clock = clock;
    }

    @Transactional(readOnly = true)
    public CardResponse get(String token) {
        DebitCard c = find(token);
        return map(c);
    }

    @Transactional
    public CardResponse block(String token) {
        DebitCard c = find(token);
        c.setStatus(CardStatus.BLOCKED);
        return map(c);
    }

    @Transactional
    public CardResponse unblock(String token) {
        DebitCard c = find(token);
        if (c.getExpiryDate().isBefore(java.time.LocalDate.now(clock)))
            throw new BusinessException("Expired card cannot be unblocked");
        c.setStatus(CardStatus.ACTIVE);
        return map(c);
    }

    @Transactional
    public AuthorizationResponse authorize(String token, AuthorizationRequest r) {
        var existing = txns.findByIdempotencyKey(r.idempotencyKey());
        if (existing.isPresent()) {
            var t = existing.get();
            return new AuthorizationResponse(t.getId(), t.getStatus(), find(token).getAvailableBalance());
        }
        DebitCard c = find(token);
        if (c.getStatus() != CardStatus.ACTIVE) throw new BusinessException("Card is not active");
        if (r.amount().compareTo(limits.singleTransaction()) > 0)
            throw new BusinessException("Single transaction limit exceeded");
        if (c.getAvailableBalance().compareTo(r.amount()) < 0) throw new BusinessException("Insufficient funds");
        c.debit(r.amount());
        CardTransaction t = txns.save(new CardTransaction(r.idempotencyKey(), r.amount(), r.currency(), r.merchant(), TransactionStatus.APPROVED, clock.instant(), c));
        return new AuthorizationResponse(t.getId(), t.getStatus(), c.getAvailableBalance());
    }

    private DebitCard find(String token) {
        return cards.findByCardToken(token).orElseThrow(() -> new NotFoundException("Card not found"));
    }

    private CardResponse map(DebitCard c) {
        return new CardResponse(c.getId(), c.getMaskedPan(), c.getStatus(), c.getAvailableBalance());
    }
}
