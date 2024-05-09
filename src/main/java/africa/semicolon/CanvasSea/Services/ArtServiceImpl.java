package africa.semicolon.CanvasSea.Services;

import africa.semicolon.CanvasSea.DTOs.Request.DisplayArtRequest;
import africa.semicolon.CanvasSea.DTOs.Request.PurchaseArtRequest;
import africa.semicolon.CanvasSea.Data.Model.Art;
import africa.semicolon.CanvasSea.Data.Model.Artist;
import africa.semicolon.CanvasSea.Data.Repository.ArtRepository;
import africa.semicolon.CanvasSea.Exceptions.ArtNotFoundException;
import africa.semicolon.CanvasSea.Utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ArtServiceImpl implements ArtService {
    @Autowired
    private ArtRepository artRepository;

    @Override
    public Art create(DisplayArtRequest displayArtRequest, Artist foundArtist) {
        if (displayArtRequest == null || foundArtist == null) {
            throw new IllegalArgumentException("Both Art and Artist's details must be filled!!");
        }

        if (displayArtRequest.getArtName() == null || displayArtRequest.getArtName().isEmpty()) {
            throw new IllegalArgumentException("Art name must be filled!!");
        }

        if (displayArtRequest.getDescription() == null || displayArtRequest.getDescription().isEmpty()) {
            throw new IllegalArgumentException("Art description must be filled!!");
        }

        if (displayArtRequest.getAmount() == null || displayArtRequest.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Art amount must not be invalid!!");
        }

        if (displayArtRequest.getImage() == null || displayArtRequest.getImage().getData() == null || displayArtRequest.getImage().getData().length == 0) {
            throw new IllegalArgumentException("Art image not found!!");
        }

        if (foundArtist.getUsername() == null || foundArtist.getUsername().isEmpty()) {
            throw new IllegalArgumentException("Artist username must be filled!!");
        }

        if (foundArtist.getEmail() == null || foundArtist.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Artist email must be filled!!");
        }

        Art art = Mapper.mapArt(displayArtRequest, foundArtist);
        artRepository.save(art);
        return art;
    }

    @Override
    public byte[] getImage(String artId) {
        Art art = findArt(artId);
        if (art != null) {
            return art.getImage().getData();
        } else {
            throw new ArtNotFoundException("Art not found");
        }
    }

    @Override
    public void save(Art art) {
        artRepository.save(art);
    }

    @Override
    public void delete(List<Art> arts) {
        artRepository.deleteAll(arts);
    }

    @Override
    public Art findArt(String artId) {
        return artRepository.findArtById(artId);
    }

    @Override
    public Long count() {
        return artRepository.count();
    }

    @Override
    public Art create(DisplayArtRequest displayArtRequest, Optional<Artist> foundArtist) {
        return new Art();
    }

    @Override
    public List<Art> findAllArt() {
        return artRepository.findAll();
    }

    @Override
    public Art findAArt(String id) {
        Optional<Art> optionalArt = artRepository.findById(id);
        if (optionalArt.isPresent()) {
            return optionalArt.get();
        } else {
            throw new ArtNotFoundException("Art not found");
        }
    }

    @Override
    public void removeAArt(String artId) {

    }

    @Override
    public void saveArt(Art art) {

    }

    @Override
    public Art findArtById(String artId) {
        return artRepository.findArtById(artId);
    }

    @Override
    public void purchaseArt(PurchaseArtRequest purchaseArtRequest) {
        Art art = artRepository.findById(purchaseArtRequest.getArtId())
                .orElseThrow(() -> new ArtNotFoundException("Artwork not found"));

        if (!art.isAvailable()) {
            throw new RuntimeException("Artwork is not available for purchase");
        }

        if (purchaseArtRequest.getAmount() == null || purchaseArtRequest.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Invalid purchase amount");
        }

        if (purchaseArtRequest.getAmount().compareTo(art.getPrice()) > 0) {
            throw new IllegalArgumentException("Purchase amount exceeds the artwork's price");
        }

        art.setAvailable(false);
        art.setPurchasedAmount();
        artRepository.save(art);
        System.out.println("Artwork purchased successfully!");
    }
}