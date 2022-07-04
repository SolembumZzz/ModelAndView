package com.codegym.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(schema = "springmvc-model-and-view", name = "transfers")
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id = 0L;

    @NotNull(message = "Transaction amount cannot be left empty")
    @Range(min = 50, max = 1000000000, message = "Transaction amount must be between 50 and 1.000.000.000")
    @Column(precision = 12)
    private BigDecimal transactionAmount = BigDecimal.ZERO;

    @Range(min = 0, max = 100)
    @Column(precision = 3)
    private BigDecimal fee = new BigDecimal(10);

    @Column(precision = 12)
    private BigDecimal feeAmount = BigDecimal.ZERO;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @Column
    private Long createdBy = null;

    @ManyToOne
    @JoinColumn(name = "senderId", referencedColumnName = "id")
    private Customer sender;

    @ManyToOne
    @JoinColumn(name = "recipientId", referencedColumnName = "id")
    private Customer recipient;

    public Transfer() {
    }

    public Transfer(Long id, BigDecimal transactionAmount, BigDecimal fee, BigDecimal feeAmount, LocalDateTime createdAt, Long createdBy, Customer sender, Customer recipient) {
        this.id = id;
        this.transactionAmount = transactionAmount;
        this.fee = fee;
        this.feeAmount = feeAmount;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.sender = sender;
        this.recipient = recipient;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public BigDecimal getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(BigDecimal feeAmount) {
        this.feeAmount = feeAmount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getcreatedBy() {
        return createdBy;
    }

    public void setcreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Customer getSender() {
        return sender;
    }

    public void setSender(Customer sender) {
        this.sender = sender;
    }

    public Customer getRecipient() {
        return recipient;
    }

    public void setRecipient(Customer recipient) {
        this.recipient = recipient;
    }
}
