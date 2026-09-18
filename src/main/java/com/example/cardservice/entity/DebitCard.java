package com.example.cardservice.entity;
import jakarta.persistence.*;import java.math.BigDecimal;import java.time.LocalDate;import java.util.ArrayList;import java.util.List;
/** Debit card state. PAN is represented only by a token and masked value. */
@Entity @Table(name="debit_cards",indexes=@Index(name="idx_card_token",columnList="card_token",unique=true))
public class DebitCard {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(name="card_token",nullable=false,unique=true,length=80) private String cardToken;
 @Column(name="masked_pan",nullable=false,length=19) private String maskedPan;
 @Enumerated(EnumType.STRING) @Column(nullable=false,length=20) private CardStatus status;
 @Column(nullable=false,precision=19,scale=2) private BigDecimal availableBalance;
 @Column(nullable=false) private LocalDate expiryDate;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="customer_id",nullable=false) private Customer customer;
 @OneToMany(mappedBy="card",cascade=CascadeType.ALL,orphanRemoval=true) private List<CardTransaction> transactions=new ArrayList<>();
 protected DebitCard(){} public DebitCard(String t,String p,BigDecimal b,LocalDate e,Customer c){cardToken=t;maskedPan=p;availableBalance=b;expiryDate=e;customer=c;status=CardStatus.ACTIVE;}
 public Long getId(){return id;} public String getCardToken(){return cardToken;} public String getMaskedPan(){return maskedPan;} public CardStatus getStatus(){return status;} public BigDecimal getAvailableBalance(){return availableBalance;} public LocalDate getExpiryDate(){return expiryDate;} public Customer getCustomer(){return customer;}
 public void setStatus(CardStatus s){status=s;} public void debit(BigDecimal amount){availableBalance=availableBalance.subtract(amount);}
}
