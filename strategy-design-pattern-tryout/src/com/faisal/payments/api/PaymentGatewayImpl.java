package com.faisal.payments.api;

import com.faisal.payments.inmemory_database.Database;
import com.faisal.payments.model.CardDetails;
import com.faisal.payments.model.Type;

import javax.xml.crypto.Data;
import java.lang.management.PlatformLoggingMXBean;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.NoSuchElementException;
import java.util.logging.Logger;

public class PaymentGatewayImpl implements PaymentGateway {
    Logger LOG = Logger.getLogger(PaymentGatewayImpl.class.getName());

    @Override
    public boolean processPayment(Type type, CardDetails from, CardDetails to, double amount, Instant instant) {
        var fromCreditCardDetailsWithBalance = validateAndGetCardDetailsWithAmount(from);
        validateAvailableBalance(amount, fromCreditCardDetailsWithBalance);

        var toCreditCardDetailsWithBalance = validateAndGetCardDetailsWithAmount(to);
        var fromSuccessFlag = deductAmountFrom(fromCreditCardDetailsWithBalance, amount);
        var toSuccessFlag = false;
        if (fromSuccessFlag == true) {
            toSuccessFlag = addAmountTo(toCreditCardDetailsWithBalance, amount);
        }

        if (fromSuccessFlag == true && toSuccessFlag == true) {
            LOG.info("Processing payment: from:" + from + " to: " + to + " amount: " + amount + " instant:" + instant);
            return true;
        } else {
            return false;
        }
    }

    private boolean addAmountTo(CardDetails toCreditCardDetailsWithBalance, double amount) {
        var currentBalance = toCreditCardDetailsWithBalance.getAvailableBalance();
        var updatedCardDetails = toCreditCardDetailsWithBalance.withAvailableBalance(currentBalance + amount);
        Database.updateCardDetails(updatedCardDetails);
        return true;
    }

    private boolean deductAmountFrom(CardDetails fromCreditCardDetailsWithBalance, double amount) {
        var currentBalance = fromCreditCardDetailsWithBalance.getAvailableBalance();
        var updatedCardDetails = fromCreditCardDetailsWithBalance.withAvailableBalance(currentBalance - amount);
        Database.updateCardDetails(updatedCardDetails);
        return true;
    }

    private static CardDetails validateAndGetCardDetailsWithAmount(CardDetails from) {
        try {
            return Database.getCreditCardDetails(from.getCardNumber());
        } catch (NoSuchElementException e) {
            throw new RuntimeException(e.getCause());
        }
    }

    private static void validateAvailableBalance(double amount, CardDetails creditCardDetailsWithBalance) {
        if (creditCardDetailsWithBalance.getAvailableBalance() < amount) {
            throw new IllegalArgumentException("Available balance is less than the amount to be deducted");
        }
    }
}