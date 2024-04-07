package africa.semicolon.CanvasSea.Data.Repository;

import africa.semicolon.CanvasSea.Data.Model.Artist;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ArtistRepository extends MongoRepository<Artist, String> {
    Optional<Artist> findByUsername(String artistName);
    Optional<Artist> findByEmail(String email);

}
