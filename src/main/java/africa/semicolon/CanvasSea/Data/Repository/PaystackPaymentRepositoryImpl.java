package africa.semicolon.CanvasSea.Data.Repository;

import africa.semicolon.CanvasSea.Data.Model.PaymentPaystack;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaystackPaymentRepositoryImpl extends MongoRepository<PaymentPaystack, String> {
}
