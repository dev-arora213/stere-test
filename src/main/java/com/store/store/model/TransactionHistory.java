package com.store.store.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.NumberFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Table(name = "transaction_history", uniqueConstraints = { @UniqueConstraint(columnNames = "paymentId") })
@Data
public class TransactionHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private Order order;

    public TransactionHistory(Order order, Status status, @NotBlank String paymentId, Float totalAmount) {
        this.order = order;
        this.status = status;
        this.paymentId = paymentId;
        this.totalAmount = totalAmount;
    }

    @NumberFormat
    Float totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Status status;

    @NotBlank
    private String paymentId;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

}
