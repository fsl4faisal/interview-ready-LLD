package com.faisal.payments.inmemory_database;

import com.faisal.payments.model.PaymentDetails;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import static com.faisal.payments.model.Type.CREDIT_CARD;
import static com.faisal.payments.model.Type.UPI;

public class Database {
    private static Map<String, PaymentDetails> cardDetailsByCardNumber = new HashMap<>();
    private static Map<String, PaymentDetails> upiDetailsByPhoneNumber = new HashMap<>();
    public static final String CARD_NUMBER1 = "1022-2390-3345-5689";
    public static final String CARD_NUMBER2 = "1022-2390-3345-5609";

    public static final String PHONE_NUMBER1 = "9911052293";

    public static final String PHONE_NUMBER2 = "992203452";

    static {
        cardDetailsByCardNumber.putAll(Map.of(
                CARD_NUMBER1, new PaymentDetails(CARD_NUMBER1, "134", CREDIT_CARD, 10000.0d),
                CARD_NUMBER2, new PaymentDetails(CARD_NUMBER2, "133", CREDIT_CARD, 3444.0d))
        );

        upiDetailsByPhoneNumber.putAll(Map.of(
                PHONE_NUMBER1, new PaymentDetails(PHONE_NUMBER1, null, UPI, 10000.0d),
                PHONE_NUMBER2, new PaymentDetails(PHONE_NUMBER2, null, UPI, 3444.0d))
        );

    }

    public static Map<String, PaymentDetails> getCardDetailsByCardNumber() {
        return cardDetailsByCardNumber;
    }

    public static void updateCardDetails(PaymentDetails cardDetail) {
        cardDetailsByCardNumber.put(cardDetail.getIdentifier(), cardDetail);
    }

    public static PaymentDetails getCreditCardDetails(String cardNumber) {
        return cardDetailsByCardNumber.keySet().stream()
                .map(i -> cardDetailsByCardNumber.get(cardNumber))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Incorrect Card Details"));
    }

    public static PaymentDetails getUpiDetails(String phoneNumber) {
        return upiDetailsByPhoneNumber.keySet().stream()
                .map(i -> upiDetailsByPhoneNumber.get(phoneNumber))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Phone Number not found"));
    }
}
