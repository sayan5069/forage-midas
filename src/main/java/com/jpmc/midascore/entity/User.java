package com.jpmc.midascore.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.math.BigDecimal;

@Entity
public class User {

    @Id
    private String userId;
    private BigDecimal balance;

    // Constructors (JPA requirement)
    public User() {}
    public User(String userId, BigDecimal balance) {
        this.userId = userId;
        this.balance = balance;
    }

    // Getters and Setters
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }
}