package africa.semicolon.CanvasSea.Services;

import africa.semicolon.CanvasSea.DTOs.Request.*;
import africa.semicolon.CanvasSea.Data.Model.*;

import java.util.List;
import java.util.Optional;

public interface ArtistService {
    Artist register(RegisterRequest registerRequest);

    Optional<Artist> findArtist(String email);

    void remove(String username, String email);

    Artist login(LoginRequest loginRequest);

    Art displayArt(DisplayArtRequest displayArtRequest);

    Optional<Artist> findArtistByEmail(String email);

    List<Art> findAllArt(String username, String email);

    Art findAArt(FindAArtRequest findAArtRequest);

    void removeAArt(RemoveAArtRequest removeAArtRequest);


}
