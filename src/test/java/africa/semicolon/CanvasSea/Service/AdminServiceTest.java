package africa.semicolon.CanvasSea.Service;

import africa.semicolon.CanvasSea.DTOs.Request.*;
import africa.semicolon.CanvasSea.Data.Model.Art;
import africa.semicolon.CanvasSea.Data.Repository.ArtRepository;
import africa.semicolon.CanvasSea.Data.Repository.ArtistRepository;
import africa.semicolon.CanvasSea.Services.AdminService;
import africa.semicolon.CanvasSea.Services.ArtistService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class AdminServiceTest {
    @Autowired
    private AdminService adminService;
    @Autowired
    private ArtistService artistService;
    @Autowired
    ArtistRepository artistRepository;
    @Autowired
    ArtRepository artRepository;

    @AfterEach
    public void deleteBeforeTest() {
        artistRepository.deleteAll();
        artRepository.deleteAll();
    }


    @Test
    void adminCanUploadArtDisplayedByArtistWithValidArtId() {
        RegisterRequest registerRequest = request("usernames", "password123", "veronica@gmail.com");
        artistService.register(registerRequest);

        LoginRequest loginRequest = loginRequest("usernames", "password123", "veronica@gmail.com");
        artistService.login(loginRequest);

        Art art;
        DisplayArtRequest displayArtRequest = artRequest("Art", BigDecimal.valueOf(1000), "usernames", "An art", "veronica@gmail.com");
        artistService.displayArt(displayArtRequest);

        AdminRequest adminRequest = adminRequest("admin@gmail.com", "admin12");
        UploadRequest uploadRequest = requestUpload(1, "veronica@gmail.com");
        art = adminService.uploadArt(adminRequest, uploadRequest);

        assertTrue(art.isPublished());
    }

    @Test
    void adminCanRemoveArtist() {
        RegisterRequest registerRequest1 = request("username", "password1", "email@gmail.com");
        artistService.register(registerRequest1);
        assertEquals(1, artistRepository.count());

        LoginRequest loginRequest = loginRequest("username", "password1", "email@gmail.com");
        artistService.login(loginRequest);

        AdminRequest adminRequest = adminRequest("www.wealthydavid@gmail.com", "admin2035");
        RemoveArtistRequest removeArtistRequest = removeArtistRequest("da_vinci", "da_vnci@gmail.com");

        adminService.removeArtist(adminRequest, removeArtistRequest);
        assertEquals(0, artistRepository.count());
    }

    @Test
    void allArtsByAnArtistAreRemovedWhenTheArtistIsRemoved() {
        RegisterRequest registerRequest12 = request("leonardo", "leonerd12", "lionleo@gmail.com");
        artistService.register(registerRequest12);
        RegisterRequest registerRequest13 = request("Triss", "Triss13", "trissart@gmail.com");
        artistService.register(registerRequest13);
        assertEquals(2, artistRepository.count());

        LoginRequest loginRequest = loginRequest("leonardo", "leonerd12", "lionleo@gmail.com");
        artistService.login(loginRequest);

        DisplayArtRequest displayArtRequest = artRequest("Art", BigDecimal.valueOf(1000), "leonardo", "An artwork", "lionleo@gmail.com");
        artistService.displayArt(displayArtRequest);
        displayArtRequest.setArtName("Monkey");
        artistService.displayArt(displayArtRequest);
        displayArtRequest.setArtName("Jump");
        artistService.displayArt(displayArtRequest);
        displayArtRequest.setArtName("Animal");
        artistService.displayArt(displayArtRequest);

        LoginRequest loginRequest2 = loginRequest("Triss", "Triss13", "trissart@gmail.com");
        artistService.login(loginRequest2);

        DisplayArtRequest displayArtRequest2 = artRequest("lily", BigDecimal.valueOf(1000), "Triss", "painting", "trissart@gmail.com");
        artistService.displayArt(displayArtRequest2);

        assertEquals(4, artRepository.findArtsByArtist_Email("lionleo@gmail.com").size());
        assertEquals(1, artRepository.findArtsByArtist_Email("trissart@gmail.com").size());

        AdminRequest adminRequest = adminRequest("www.wealthydavid@gmail.com", "admin2035");
        RemoveArtistRequest removeArtistRequest = removeArtistRequest("da_vinci", "da_vinci@gmail.com");

        adminService.removeArtist(adminRequest, removeArtistRequest);
        assertEquals(0, artRepository.findArtsByArtist_Email("da_vinci@gmail.com").size());
        assertEquals(1, artRepository.findArtsByArtist_Email("lionleo@gmail.com").size());

    }

    private UploadRequest requestUpload(String artId, String email) {
        UploadRequest uploadRequest = new UploadRequest();
        uploadRequest.setArtId(artId);
        uploadRequest.setEmail(email);
        return uploadRequest;
    }

    private RegisterRequest request(String username, String password, String email) {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername(username);
        registerRequest.setPassword(password);
        registerRequest.setEmail(email);
        return registerRequest;
    }

    private DisplayArtRequest artRequest(String artName, BigDecimal amount, String artistUsername, String description, String email) {
        DisplayArtRequest displayArtRequest = new DisplayArtRequest();
        displayArtRequest.setArtName(artName);
        displayArtRequest.setAmount(amount);
        displayArtRequest.setEmail(email);
        displayArtRequest.setArtistUsername(artistUsername);
        displayArtRequest.setDescription(description);

        return displayArtRequest;
    }

    private LoginRequest loginRequest(String username, String password, String email) {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(username);
        loginRequest.setPassword(password);
        loginRequest.setEmail(email);
        return loginRequest;
    }

    private AdminRequest adminRequest(String email, String password) {
        AdminRequest adminRequest = new AdminRequest();
        adminRequest.setEmail(email);
        adminRequest.setPassword(password);
        return adminRequest;
    }

    private RemoveArtistRequest removeArtistRequest(String username, String email) {
        RemoveArtistRequest removeArtistRequest = new RemoveArtistRequest();
        removeArtistRequest.setUsername(username);
        removeArtistRequest.setEmail(email);
        return removeArtistRequest;
    }
}
