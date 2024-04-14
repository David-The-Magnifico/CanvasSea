package africa.semicolon.CanvasSea.Services;

import africa.semicolon.CanvasSea.DTOs.Request.DisplayArtRequest;
import africa.semicolon.CanvasSea.Data.Model.Art;
import africa.semicolon.CanvasSea.Data.Model.Artist;

import java.util.List;
import java.util.Optional;

public interface ArtService {
    Art findArt(String id);
    Long count();

    Art create(DisplayArtRequest displayArtRequest, Optional<Artist> foundArtist);
    void delete(List<Art> arts);

    Art create(DisplayArtRequest displayArtRequest, Artist foundArtist);

    void save(Art art);
    List<Art> findAllArt();
    Art findAArt(String artId);
    void removeAArt(String artId);
    void saveArt(Art art);
    Art findArtById(String artId);
}
