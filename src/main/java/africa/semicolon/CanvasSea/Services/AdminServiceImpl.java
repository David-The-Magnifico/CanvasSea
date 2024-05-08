package africa.semicolon.CanvasSea.Services;

import africa.semicolon.CanvasSea.DTOs.Request.*;
import africa.semicolon.CanvasSea.Data.Model.*;
import africa.semicolon.CanvasSea.Data.Repository.ArtRepository;
import africa.semicolon.CanvasSea.Exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static africa.semicolon.CanvasSea.Utils.Mapper.mapAdmin;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    ArtistService artistService;
    @Autowired
    ArtService artService;
    @Autowired
    ArtRepository artRepository;

    @Override
    public Art uploadArt(AdminRequest adminRequest, UploadRequest uploadRequest) {
        confirmAdmin(adminRequest, adminRequest.getEmail(), uploadRequest.getPassword());

        Artist artist = artistService.findArtistByEmail(uploadRequest.getEmail());
        if (artist == null) {
            throw new UserNotFoundException("Error! No artist with this email exists");
        }

        Art art = artService.findArt(uploadRequest.getArtId());
        if (art == null) {
            throw new ArtNotFoundException("Error! No art with this id exists");
        }

        if (!art.isAvailable()) {
            art.setAvailable(true);
            artRepository.save(art);
        }
        return art;
    }

    @Override
    public void removeArtist(AdminRequest adminRequest, RemoveArtistRequest removeArtistRequest) {
        confirmAdmin(adminRequest, adminRequest.getEmail(), adminRequest.getPassword());

        Artist artist = artistService.findArtist(removeArtistRequest.getUsername());
        if (artist == null) {
            throw new UserNotFoundException("Error! No artist with this username exists");
        }

        artistService.remove(removeArtistRequest.getUsername(), removeArtistRequest.getEmail());
    }

    @Override
    public void confirmAdmin(AdminRequest adminRequest, String email, String password) {
        Admin admin = mapAdmin(email, password);
        if (!adminRequest.getEmail().equals(email) || !adminRequest.getPassword().equals(password)) {
            throw new InvalidDetailsException("Error! Wrong password or Email");
        }
    }
}
