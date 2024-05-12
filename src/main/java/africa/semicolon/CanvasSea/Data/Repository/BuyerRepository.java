package africa.semicolon.CanvasSea.Data.Repository;

import africa.semicolon.CanvasSea.Data.Model.Buyer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuyerRepository extends MongoRepository<Buyer, String> {
    Buyer findByUsername(String buyerName);
    Buyer findByEmail(String email);
}
