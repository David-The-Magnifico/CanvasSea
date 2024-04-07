package africa.semicolon.CanvasSea.Data.Repository;

import africa.semicolon.CanvasSea.Data.Model.Admin;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AdminRepository extends MongoRepository<Admin, String> {
    Admin findByUsername(String admin);
}
