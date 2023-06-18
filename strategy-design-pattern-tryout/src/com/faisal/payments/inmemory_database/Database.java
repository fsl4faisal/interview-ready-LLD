package com.faisal.payments.inmemory_database;

import com.faisal.payments.model.CardDetails;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import static com.faisal.payments.model.Type.CREDIT_CARD;

public class Database {
    private static Map<String, CardDetails> cardDetailsByCardNumber = new HashMap<>();
    public static final String CARD_NUMBER1 = "1022-2390-3345-5689";
    public static final String CARD_NUMBER2 = "1022-2390-3345-5609";

    static {
        cardDetailsByCardNumber.putAll(Map.of(
                CARD_NUMBER1, new CardDetails(CARD_NUMBER1, "134", CREDIT_CARD, 10000.0d),
                CARD_NUMBER2, new CardDetails(CARD_NUMBER2, "133", CREDIT_CARD, 3444.0d))
        );
    }

    public static Map<String, CardDetails> getCardDetailsByCardNumber() {
        return cardDetailsByCardNumber;
    }

    public static void updateCardDetails(CardDetails cardDetail) {
        cardDetailsByCardNumber.put(cardDetail.getCardNumber(), cardDetail);
    }

    public static CardDetails getCreditCardDetails(String cardNumber) {
        return cardDetailsByCardNumber.keySet().stream()
                .map(i -> cardDetailsByCardNumber.get(cardNumber))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Incorrect Card Details"));
    }
}
