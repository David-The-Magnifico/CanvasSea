package africa.semicolon.CanvasSea.Data.Repository;

import africa.semicolon.CanvasSea.Data.Model.Buyer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface BuyerRepository extends MongoRepository<Buyer, String> {
    Optional<Buyer> findByUsername(String buyerName);
    Optional<Buyer> findByEmail(String email);
}
