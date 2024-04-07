package africa.semicolon.CanvasSea.Utils;

import africa.semicolon.CanvasSea.DTOs.Request.DisplayArtRequest;
import africa.semicolon.CanvasSea.DTOs.Request.RegisterRequest;
import africa.semicolon.CanvasSea.Data.Model.Admin;
import africa.semicolon.CanvasSea.Data.Model.Art;
import africa.semicolon.CanvasSea.Data.Model.Artist;
import africa.semicolon.CanvasSea.Data.Model.Buyer;

public class Mapper {

    public  static Artist artistMapper(RegisterRequest registerRequest){
        Artist artist = new Artist();
        artist.setUsername(registerRequest.getUsername());
        artist.setPassword(registerRequest.getPassword());
        artist.setEmail(registerRequest.getEmail());
        return artist;
    }

    public  static Buyer buyerMapper(RegisterRequest registerRequest){
        Buyer buyer = new Buyer();
        buyer.setUsername(registerRequest.getUsername());
        buyer.setPassword(registerRequest.getPassword());
        buyer.setEmail(registerRequest.getEmail());
        return buyer;
    }

    public static Art mapArt(DisplayArtRequest displayArtRequest, Artist foundArtist) {
        Art art = new Art();
        art.setArtist(foundArtist);
        art.setDescription(displayArtRequest.getDescription());
        art.setName(displayArtRequest.getArtName());
        art.setPrice(displayArtRequest.getAmount());
        return art;
    }

    public static Art map(String name, String id){
        Art art = new Art();
        art.setName(name);
        art.setArtId(id);
        return art;
    }

    public static Admin mapAdmin(String email, String password){
        Admin admin = new Admin();
        admin.setEmail("www.wealthydavid@gmail.com");
        admin.setPassword("admin2035");
        return admin;
    }
}
