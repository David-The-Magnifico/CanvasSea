package africa.semicolon.CanvasSea.Services;

import africa.semicolon.CanvasSea.DTOs.Request.DisplayArtRequest;
import africa.semicolon.CanvasSea.Data.Model.Art;
import africa.semicolon.CanvasSea.Data.Model.Artist;

import java.util.List;

public interface ArtService {
    Art findArt(String artId);
    Long count();
    List<Art> findArtsOwnedBy(String email);
    Art create(DisplayArtRequest displayArtRequest, Artist foundArtist);
    void delete(List<Art> arts);

    void save(Art art);
    List<Art> findAllArt();
    Art findAArt(String artId);
    void removeAArt(String artId);
}
