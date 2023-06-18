import com.faisal.payments.api.PaymentGatewayImpl;
import com.faisal.payments.inmemory_database.Database;
import com.faisal.payments.model.Type;

import javax.xml.crypto.Data;
import java.time.Instant;

public class Main {
    public static void main(String[] args) {
        System.out.printf("Hello and welcome!");

        var v = new PaymentGatewayImpl();
        v.processPayment(
                Database.getCreditCardDetails(Database.CARD_NUMBER1),
                Database.getCreditCardDetails(Database.CARD_NUMBER2),
                12,
                Instant.now());

        v.processPayment(
                Database.getCreditCardDetails(Database.CARD_NUMBER1),
                Database.getUpiDetails(Database.PHONE_NUMBER1),
                122,
                Instant.now());


    }
}