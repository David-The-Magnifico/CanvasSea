package africa.semicolon.CanvasSea.Data.Repository;

import africa.semicolon.CanvasSea.Data.Model.Art;
import africa.semicolon.CanvasSea.Data.Model.Artist;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ArtistRepository extends MongoRepository<Artist, String> {
    Artist findByUsername(String artistName);
    Artist findByEmail(String email);
}
