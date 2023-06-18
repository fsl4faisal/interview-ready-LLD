import com.faisal.payments.api.PaymentGatewayImpl;
import com.faisal.payments.inmemory_database.Database;
import com.faisal.payments.model.TestModel;
import com.faisal.payments.model.Type;

import java.sql.Timestamp;
import java.time.Instant;

public class Main {
    public static void main(String[] args) {
        System.out.printf("Hello and welcome!");

        var v = new PaymentGatewayImpl();
        v.processPayment(Type.CREDIT_CARD, Database.getCreditCardDetails(Database.CARD_NUMBER1), Database.getCreditCardDetails(Database.CARD_NUMBER2), 12, Instant.now());


    }
}