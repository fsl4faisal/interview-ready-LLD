package com.faisal.payments.api;

import com.faisal.payments.model.CardDetails;
import com.faisal.payments.model.Type;

import java.sql.Timestamp;

public class PaymentGatewayImpl implements PaymentGateway {

    @Override
    public void processPayment(Type type, CardDetails from, CardDetails to, double amount, Timestamp timestamp) {
        //check available balance in "from"

        //deduct the amount from
        //add available balance in "to"
    }
}
