package africa.semicolon.CanvasSea.Data.Repository;

import africa.semicolon.CanvasSea.Data.Model.Art;
import africa.semicolon.CanvasSea.Data.Model.Artist;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ArtRepository extends MongoRepository<Art, String> {
    Optional<Art> findByArtist(String artId);
    Art findArtById(String artId);
    List<Art> findArtsByArtist_Email(String email);

    Optional<Object> findByArtistUsernameOrArtistEmail(String username, String email);
}
