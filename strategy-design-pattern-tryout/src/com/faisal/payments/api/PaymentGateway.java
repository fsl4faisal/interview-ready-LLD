package com.faisal.payments.api;

/*

id can be anything from UPI_ID to credit_card,
UPI: from_id, to_id, timestamp, amount,
CreditCard:
 */


import com.faisal.payments.model.CardDetails;
import com.faisal.payments.model.Type;

import java.time.Instant;

public interface PaymentGateway {
    boolean processPayment(Type type, CardDetails from, CardDetails to, double amount, Instant instant) throws Exception;
}
