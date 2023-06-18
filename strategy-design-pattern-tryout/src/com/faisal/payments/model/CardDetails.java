package com.faisal.payments.model;

import lombok.*;

@AllArgsConstructor
@With
@Getter
@Builder
@ToString
public class CardDetails {
    @NonNull
    private final String cardNumber;
    @NonNull
    private final String cvv;
    @NonNull
    private final Type type;
    @NonNull
    private final double availableBalance;//this information will be filled by Bank Database

    public double getAvailableBalance() {
        return availableBalance;
    }

    public void deductAmount(double amount) {

    }

    public void addAmount(double amount) {

    }
}
