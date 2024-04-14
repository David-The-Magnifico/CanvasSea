package africa.semicolon.CanvasSea.Services;


import africa.semicolon.CanvasSea.DTOs.Request.*;
import africa.semicolon.CanvasSea.Data.Model.Art;
import africa.semicolon.CanvasSea.Data.Model.Artist;
import africa.semicolon.CanvasSea.Data.Repository.ArtistRepository;
import africa.semicolon.CanvasSea.Exceptions.*;
import africa.semicolon.CanvasSea.Utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static africa.semicolon.CanvasSea.Utils.Mapper.artistMapper;

@Service
public class ArtistServiceImpl implements ArtistService {
    @Autowired
    private ArtistRepository artistRepository;
    @Autowired
    private ArtService artService;



    @Override
    public Artist register(RegisterRequest registerRequest) {
        if (checkIfArtistExist(registerRequest.getUsername(), registerRequest.getEmail()))
            throw new ArtistExistException("Artist already exist\t" + registerRequest.getUsername());
        validations(registerRequest);
        Artist artist = artistMapper(registerRequest);
        return artistRepository.save(artist);
    }

    @Override
    public Artist login(LoginRequest loginRequest) {
        if (!checkIfArtistExist(loginRequest.getUsername(), loginRequest.getEmail()))
            throw new ArtistExistException("Artist May Not exist");
        Optional<Artist> foundArtist = Optional.ofNullable(artistRepository.findByUsername(loginRequest.getUsername()));
        if (!foundArtist.get().getPassword().equals(loginRequest.getPassword()))
            throw new InvalidDetailsException("Details entered are invalid");
        foundArtist.get().setLoggedIn(true);
        return artistRepository.save(foundArtist.get());
    }

    @Override
    public Art displayArt(DisplayArtRequest displayArtRequest) {
        if (!checkIfArtistExist(displayArtRequest.getArtistUsername(), displayArtRequest.getEmail())) {
            throw new ArtistExistException("Artist does not exist");
        }

        Artist artist = findArtist(displayArtRequest.getArtistUsername());
        if (artist == null) {
            throw new ArtistExistException("Artist not found");
        }

        if (!artist.isLoggedIn()) {
            throw new InvalidLoginDetails("User has not logged in");
        }

        Art art = artService.create(displayArtRequest, Optional.of(artist));
        return art;
    }



    @Override
    public Artist findArtistByEmail(String email) {
        return artistRepository.findByEmail(email);
    }

    @Override
    public List<Art> findAllArt(String username, String email) {
        return artistRepository.findAllArt(username, email);
    }

    @Override
    public Art findAArt(FindAArtRequest findAArtRequest) {
        Artist artist = artistRepository.findByEmail(findAArtRequest.getEmail());
        if (artist.isPresent()) {
            return artService.findAArt(findAArtRequest.getArtId());
        }
        throw new ArtNotFoundException("Art does not exist");
    }

    @Override
    public void removeAArt(RemoveAArtRequest removeAArtRequest) {
        Artist artist = artistRepository.findByEmail(removeAArtRequest.getEmail());
        if (artist.isPresent()) {

        }
    }

    @Override
    public Artist findArtist(String artistUsername) {
        return artistRepository.findByUsername(artistUsername);
    }



    public void validations(RegisterRequest registerRequest) {
        if (!Validator.validateName(registerRequest.getUsername()))
            throw new InvalidUsernameException("Invalid username");
        if (!Validator.validatePassword(registerRequest.getPassword()))
            throw new InvalidPasswordException("Invalid password");
        if (!Validator.validateEmail(registerRequest.getEmail())) throw new InvalidEmailException("Invalid Email");
    }

    public boolean checkIfArtistExist(String artistUsername, String email) {
        return artistRepository.findByUsername(artistUsername).isPresent() && artistRepository.findByEmail(email).isPresent();
    }

    @Override
    public void remove(String username, String email) {
        List<Art> arts = findAllArt(username, email);
        artService.delete(arts);
        Artist artist = findArtistEmail(email);

        if (artist == null) {
            throw new UserNotFoundException("Error! Artist with this email is not found");
        }

        artistRepository.delete(artist);
    }


    public Artist findArtistEmail(String email) {
    return artistRepository.findByEmail(email);
}


}
