# Debit Card Microservice

A runnable Spring Boot banking example for card lookup, block/unblock, debit authorization, idempotency, validation, persistence, global errors, configuration, and scheduling. Kafka dependencies and annotations are intentionally excluded.

## Run
```bash
mvn spring-boot:run
```
Demo token: `tok_demo_001`

## APIs
```bash
curl http://localhost:8080/api/v1/cards/tok_demo_001
curl -X POST http://localhost:8080/api/v1/cards/tok_demo_001/authorizations -H 'Content-Type: application/json' -d '{"idempotencyKey":"order-1001","amount":125.50,"currency":"INR","merchant":"Demo Store"}'
curl -X POST http://localhost:8080/api/v1/cards/tok_demo_001/block
```

## Annotation coverage
- Startup: `@SpringBootApplication`
- REST: `@RestController`, `@RequestMapping`, `@GetMapping`, `@PostMapping`, `@RequestBody`, `@PathVariable`, `@RequestParam`
- Validation: `@Valid`, plus Jakarta constraints
- Beans: `@Service`, `@Component`, `@Repository`
- JPA: `@Entity`, `@Table`, `@Id`, `@GeneratedValue`, `@Column`, `@OneToMany`, `@ManyToOne`, `@JoinColumn`, `@Enumerated`, `@Query`, `@Modifying`, `@EntityGraph`
- Transactions: `@Transactional`
- Configuration: `@Configuration`, `@Bean`, `@Value`, `@ConfigurationProperties`
- Errors: `@ControllerAdvice`, `@ExceptionHandler`
- Scheduling: `@Scheduled`

## Production notes
- Never store raw PAN/CVV. Use a PCI-compliant vault/tokenization provider.
- Replace H2 and `ddl-auto` with PostgreSQL plus Flyway/Liquibase.
- Add OAuth2 resource-server security, authorization scopes, audit trails, encryption, rate limits, tracing, and secret management.
- Idempotency prevents retry duplication. For real concurrency, add pessimistic/optimistic locking and database constraints.
- The daily limit property is included for extension; a production implementation should aggregate approved daily transactions atomically.
