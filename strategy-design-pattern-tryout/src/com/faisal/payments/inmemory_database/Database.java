package com.faisal.payments.inmemory_database;

import com.faisal.payments.model.PaymentDetails;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import static com.faisal.payments.model.Type.*;

public class Database {
    private static Map<String, PaymentDetails> cardDetailsByCardNumber = new HashMap<>();
    private static Map<String, PaymentDetails> upiDetailsByPhoneNumber = new HashMap<>();
    private static Map<String, PaymentDetails> paypalDetailsByUsername = new HashMap<>();
    public static final String CARD_NUMBER1 = "1022-2390-3345-5689";
    public static final String CARD_NUMBER2 = "1022-2390-3345-5609";

    public static final String PHONE_NUMBER1 = "9911052293";

    public static final String PHONE_NUMBER2 = "992203452";

    public static final String PAYPAL_USERNAME1 = "fsl4faisal";

    public static final String PAYPAL_USERNAME2 = "raza";

    static {
        cardDetailsByCardNumber.putAll(Map.of(
                CARD_NUMBER1, new PaymentDetails(CARD_NUMBER1, "134", CREDIT_CARD, 10000.0d),
                CARD_NUMBER2, new PaymentDetails(CARD_NUMBER2, "133", CREDIT_CARD, 3444.0d))
        );

        upiDetailsByPhoneNumber.putAll(Map.of(
                PHONE_NUMBER1, new PaymentDetails(PHONE_NUMBER1, null, UPI, 10000.0d),
                PHONE_NUMBER2, new PaymentDetails(PHONE_NUMBER2, null, UPI, 3444.0d))
        );

        paypalDetailsByUsername.putAll(Map.of(
                PAYPAL_USERNAME1, new PaymentDetails(PAYPAL_USERNAME1, null, PAYPAL, 10000.0d),
                PAYPAL_USERNAME2, new PaymentDetails(PAYPAL_USERNAME2, null, PAYPAL, 3444.0d))
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

    public static PaymentDetails getPaypalDetails(String username) {
        return paypalDetailsByUsername.keySet().stream()
                .map(i -> paypalDetailsByUsername.get(username))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("username not found"));
    }
}
