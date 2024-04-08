package africa.semicolon.CanvasSea.Services;

import africa.semicolon.CanvasSea.DTOs.Request.DisplayArtRequest;
import africa.semicolon.CanvasSea.Data.Model.*;
import africa.semicolon.CanvasSea.Data.Repository.ArtRepository;
import africa.semicolon.CanvasSea.Exceptions.*;
import africa.semicolon.CanvasSea.Utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArtServiceImpl implements ArtService {
    @Autowired
    private ArtRepository artRepository;
    @Override
    public Art create(DisplayArtRequest displayArtRequest, Artist foundArtist) {
        Art art = Mapper.mapArt(displayArtRequest, foundArtist);
        save(art);
        return art;
    }
    @Override
    public void save(Art art) {
        artRepository.save(art);
    }
    @Override
    public  void delete(List<Art> arts) {artRepository.deleteAll(arts);
    }
    @Override
    public Art findArt(String artId) {
        Art art = artRepository.findArtByArtId(artId);
        return art;
    }
    @Override
    public Long count() {
        return artRepository.count();
    }
    @Override
    public List<Art> findArtsOwnedBy(String email) {
        return artRepository.findArtsByArtist_Email(email);
    }
    @Override
    public List<Art> findAllArt() {
        return artRepository.findAll();
    }
    @Override
    public Art findAArt(String artId) {
        return artRepository.findById(artId).get();
    }
    @Override
    public void removeAArt(String artId) {
        Art art = findAArt(artId);
        if (art.isPublished()) {
            artRepository.delete(findAArt(artId));
        }
        throw new ArtNotFoundException("Art not found");
    }

    @Override
    public void saveArt(Art art) {
        artRepository.save(art);
    }

    @Override
    public Optional<Art> findArtById(String artId) {
        return artRepository.findById(artId);
    }
}