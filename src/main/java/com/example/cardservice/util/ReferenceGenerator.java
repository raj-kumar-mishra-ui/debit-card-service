package com.example.cardservice.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Generic Spring component used to create non-sensitive references.
 */
@Component
public class ReferenceGenerator {
    public String next() {
        return UUID.randomUUID().toString();
    }
}
