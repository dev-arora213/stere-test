package com.store.store.model;

import lombok.Data;

@Data
public class ChargeRequest {

    public enum Currency {
        INR;
    }

    private String description;
    private int amount;
    private Currency currency;
    private String stripeToken;
}
