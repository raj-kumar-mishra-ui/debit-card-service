package com.example.cardservice.controller;

import com.example.cardservice.dto.*;
import com.example.cardservice.service.CardService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * REST boundary for debit-card operations.
 */
@RestController
@RequestMapping("/api/v1/cards")
@Validated
public class CardController {
    private final CardService service;

    public CardController(CardService service) {
        this.service = service;
    }

    @GetMapping("/{token}")
    CardResponse get(@PathVariable @NotBlank String token, @RequestParam(defaultValue = "false") boolean includeTransactions) {
        return service.get(token);
    }

    @PostMapping("/{token}/authorizations")
    ResponseEntity<AuthorizationResponse> authorize(@PathVariable String token, @Valid @RequestBody AuthorizationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.authorize(token, request));
    }

    @PostMapping("/{token}/block")
    CardResponse block(@PathVariable String token) {
        return service.block(token);
    }

    @PostMapping("/{token}/unblock")
    CardResponse unblock(@PathVariable String token) {
        return service.unblock(token);
    }
}
