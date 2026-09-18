package com.example.cardservice.repository;

import com.example.cardservice.entity.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;

@Repository
public interface DebitCardRepository extends JpaRepository<DebitCard, Long> {
    @EntityGraph(attributePaths = "customer")
    Optional<DebitCard> findByCardToken(String token);

    @Query("select c from DebitCard c where c.status=:status and c.expiryDate<:today")
    List<DebitCard> findExpired(@Param("status") CardStatus status, @Param("today") LocalDate today);

    @Modifying
    @Query("update DebitCard c set c.status=:newStatus where c.status=:oldStatus and c.expiryDate<:today")
    int expireCards(@Param("oldStatus") CardStatus oldStatus, @Param("newStatus") CardStatus newStatus, @Param("today") LocalDate today);
}
