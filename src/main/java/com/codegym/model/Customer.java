package com.codegym.model;


import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id = 0L;
    @Length(message = "Customer name has to be between 5 and 50 characters", min = 1, max = 50)
    @Column(length = 50)
    private String fullName;
    @Length(message = "Email has to be between 5 and 50 characters", min = 5, max = 50)
    @Column(length = 50)
    private String email;
    @Length(message = "Phone number has to be between 5 and 20 characters", min = 5, max = 20)
    @Column(length = 20)
    private String phone;
    @Length(message = "Address has to be between 5 and 200 characters", min = 5, max = 200)
    @Column(length = 200)
    private String address;
    @Column(precision = 12)
    private BigDecimal balance = BigDecimal.ZERO;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
    @Column
    private Long createdBy = null;
    @UpdateTimestamp
    @Column
    private LocalDateTime updatedAt;
    @Column
    private Long updatedBy = null;
    @Column
    private boolean deleted = false;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    private List<Deposit> deposits;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    private List<Withdraw> withdraws;

    public Customer() {
    }

    public Customer(String fullName, String email, String phone, String address) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    public Customer(Long id, String fullName, String email, String phone, String address, BigDecimal balance) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.balance = balance;
    }

    public Customer(Long id, String fullName, String email, String phone, String address, BigDecimal balance, LocalDateTime createdAt, Long createdBy, LocalDateTime updatedAt, Long updatedBy, boolean deleted) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.balance = balance;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
        this.deleted = deleted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
