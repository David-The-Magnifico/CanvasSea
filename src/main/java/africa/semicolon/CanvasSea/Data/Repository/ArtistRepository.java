package africa.semicolon.CanvasSea.Data.Repository;

import africa.semicolon.CanvasSea.Data.Model.Artist;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArtistRepository extends MongoRepository<Artist, String> {
    Artist findByUsername(String artistName);
    Artist findByEmail(String email);
    Optional<Artist> findById(String id);
}
