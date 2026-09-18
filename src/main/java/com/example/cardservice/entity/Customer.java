package com.example.cardservice.entity;
import jakarta.persistence.*;import java.util.ArrayList;import java.util.List;
/** Bank customer aggregate owner. */
@Entity @Table(name="customers")
public class Customer {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,unique=true,length=40) private String customerNumber;
 @Column(nullable=false,length=120) private String fullName;
 @OneToMany(mappedBy="customer",cascade=CascadeType.ALL,orphanRemoval=true) private List<DebitCard> cards=new ArrayList<>();
 protected Customer(){} public Customer(String n,String f){customerNumber=n;fullName=f;} public Long getId(){return id;} public String getCustomerNumber(){return customerNumber;} public String getFullName(){return fullName;} public List<DebitCard> getCards(){return cards;}
}
