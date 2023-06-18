package com.faisal.payments.model;

import lombok.Builder;

@Builder
public class CardDetails {
    private final String cardNumber;
    private final String cvv;
    private final String id;
    private final Type type;
    private final double availableBalance;

    public double getAvailableBalance() {
        return availableBalance;
    }

    public void deductAmount(double amount) {

    }

    public void addAmount(double amount) {

    }
}
