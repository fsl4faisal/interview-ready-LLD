package com.faisal.payments.api;

import com.faisal.payments.inmemory_database.Database;
import com.faisal.payments.model.PaymentDetails;

import java.time.Instant;
import java.util.NoSuchElementException;
import java.util.logging.Logger;

public class PaymentGatewayImpl implements PaymentGateway {
    Logger LOG = Logger.getLogger(PaymentGatewayImpl.class.getName());

    @Override
    public boolean processPayment(PaymentDetails from, PaymentDetails to, double amount, Instant instant) {
        var fromPaymentDetailsWithBalance = validateAndGetPaymentDetailsWithAmount(from);
        validateAvailableBalance(amount, fromPaymentDetailsWithBalance);

        var toCreditCardDetailsWithBalance = validateAndGetPaymentDetailsWithAmount(to);
        var fromSuccessFlag = deductAmountFrom(fromPaymentDetailsWithBalance, amount);
        var toSuccessFlag = false;
        if (fromSuccessFlag) {
            toSuccessFlag = addAmountTo(toCreditCardDetailsWithBalance, amount);
        }

        if (fromSuccessFlag && toSuccessFlag) {
            LOG.info("Processing payment: from:" + from + " to: " + to + " amount: " + amount + " instant:" + instant);
            return true;
        } else {
            return false;
        }
    }

    private boolean addAmountTo(PaymentDetails toPaymentDetailsWithBalance, double amount) {
        var currentBalance = toPaymentDetailsWithBalance.getAvailableBalance();
        var updatedCardDetails = toPaymentDetailsWithBalance.withAvailableBalance(currentBalance + amount);
        Database.updateCardDetails(updatedCardDetails);
        return true;
    }

    private boolean deductAmountFrom(PaymentDetails fromCreditCardDetailsWithBalance, double amount) {
        var currentBalance = fromCreditCardDetailsWithBalance.getAvailableBalance();
        var updatedCardDetails = fromCreditCardDetailsWithBalance.withAvailableBalance(currentBalance - amount);
        Database.updateCardDetails(updatedCardDetails);
        return true;
    }

    private static PaymentDetails validateAndGetPaymentDetailsWithAmount(PaymentDetails from) {
        PaymentDetails paymentDetails = null;
        try {
            switch (from.getType()) {
                case UPI -> paymentDetails = Database.getUpiDetails(from.getIdentifier());
                case CREDIT_CARD -> paymentDetails = Database.getCreditCardDetails(from.getIdentifier());
            }
        } catch (NoSuchElementException e) {
            throw new RuntimeException(e.getCause());
        }
        return paymentDetails;
    }

    private static void validateAvailableBalance(double amount, PaymentDetails paymentDetailsWithBalance) {
        if (paymentDetailsWithBalance.getAvailableBalance() < amount) {
            throw new IllegalArgumentException("Available balance is less than the amount to be deducted");
        }
    }
}