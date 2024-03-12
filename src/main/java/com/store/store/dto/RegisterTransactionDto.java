package com.store.store.dto;

import com.store.store.model.Status;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterTransactionDto {
    public Long orderId;
    public String paymentId;
    public Status status;
}
