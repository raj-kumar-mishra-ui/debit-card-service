package com.example.cardservice.service;

import com.example.cardservice.entity.CardStatus;
import com.example.cardservice.repository.DebitCardRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDate;

/**
 * Periodically moves expired active cards to EXPIRED.
 */
@Component
public class CardExpiryJob {
    private final DebitCardRepository r;
    private final Clock c;

    public CardExpiryJob(DebitCardRepository r, Clock c) {
        this.r = r;
        this.c = c;
    }

    @Scheduled(cron = "${card.expiry-cron:0 0 1 * * *}")
    @Transactional
    public void expire() {
        r.expireCards(CardStatus.ACTIVE, CardStatus.EXPIRED, LocalDate.now(c));
    }
}
