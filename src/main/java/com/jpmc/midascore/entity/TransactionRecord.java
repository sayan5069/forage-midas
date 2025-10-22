package com.jpmc.midascore.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class TransactionRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id") // Foreign key column
    private User sender;

    private String recipientId;
    private BigDecimal amount;
    
    // START OF TASK 4 ADDITION
    private BigDecimal incentiveAmount; // <-- NEW FIELD to store the incentive
    // END OF TASK 4 ADDITION
    
    private LocalDateTime timestamp = LocalDateTime.now(); // Record creation time

    // Constructors (JPA requirement)
    public TransactionRecord() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public User getSender() { return sender; }
    public void setSender(User sender) { this.sender = sender; }
    public String getRecipientId() { return recipientId; }
    public void setRecipientId(String recipientId) { this.recipientId = recipientId; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    
    // START OF TASK 4 GETTER/SETTER
    public BigDecimal getIncentiveAmount() { return incentiveAmount; }
    public void setIncentiveAmount(BigDecimal incentiveAmount) { this.incentiveAmount = incentiveAmount; }
    // END OF TASK 4 GETTER/SETTER
    
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}