package africa.semicolon.CanvasSea.Data.Repository;

import africa.semicolon.CanvasSea.Data.Model.Art;
import africa.semicolon.CanvasSea.Data.Model.Artist;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ArtRepository extends MongoRepository<Art, String> {
    Optional<Art> findByArtist(String artId);

    Art findArtByIdAndArtist(String ArtId, Artist artistId);
    Art findArtById(String artId);
    List<Art> findArtsByArtist_Email(String email);
}
