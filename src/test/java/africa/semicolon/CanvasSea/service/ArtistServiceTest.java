package africa.semicolon.CanvasSea.service;

import africa.semicolon.CanvasSea.DTOs.Request.*;
import africa.semicolon.CanvasSea.Data.Model.Art;
import africa.semicolon.CanvasSea.Data.Repository.ArtRepository;
import africa.semicolon.CanvasSea.Data.Repository.ArtistRepository;
import africa.semicolon.CanvasSea.Exceptions.*;
import africa.semicolon.CanvasSea.Services.ArtistService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class ArtistServiceTest {

    @Autowired
    private ArtistService artistService;
    @Autowired
    private ArtistRepository artistRepository;
    @Autowired
    private ArtRepository artRepository;
    @AfterEach
    public void deleteBeforeTest(){
        artistRepository.deleteAll();
        artRepository.deleteAll();
    }
    @Test
    public void testThatAnArtist_CanRegisterWithWrongUsernameThrowsException(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("bimbim");
        registerRequest.setPassword("assertTrue");
        registerRequest.setEmail("asserttruebim@gmail.com");
        assertThrows(InvalidUsernameException.class,()->artistService.register(registerRequest));
    }
    @Test
    public void testWhenArtistRegisterWithWrongPasswordThrowsException(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("Precious");
        registerRequest.setPassword("onome3778");
        registerRequest.setEmail("preshonome@gmail.com");
        assertThrows(InvalidPasswordException.class, ()->artistService.register(registerRequest));
    }
    @Test
    public void testThatWhenArtistRegisterWithWrongEmailThrowsException(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("Precious");
        registerRequest.setPassword("onome3778");
        registerRequest.setEmail("preshonome@gmail.com");
        assertThrows(InvalidEmailException.class, ()->artistService.register(registerRequest));
    }
    @Test
    public void testThatArtistRegisterWithCorrectInfoReturnsArtistObjects(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("Precious");
        registerRequest.setPassword("onome3778");
        registerRequest.setEmail("preshoome@gmail.com");
        artistService.register(registerRequest);
        assertEquals(1, artistRepository.count());
    }

    @Test
    public void testThatArtistCanRegisterAgainWithCorrectInfoReturnsArtistObjects(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("Precious");
        registerRequest.setPassword("onome3778");
        registerRequest.setEmail("preshonome@gmail.com");
        artistService.register(registerRequest);
        assertThrows(ArtistExistException.class,()->artistService.register(registerRequest));
    }

    @Test
    public void testThatArtistCan_RegisterAndLogin(){
        RegisterRequest registerRequest = new RegisterRequest();
        LoginRequest loginRequest = new LoginRequest();
        registerRequest.setUsername("Davido");
        registerRequest.setPassword("DavidoOBO");
        registerRequest.setEmail("30billiongang@gmail.com");
        artistService.register(registerRequest);
        loginRequest.setUsername("Davido");
        loginRequest.setPassword("DavidoOBO");
        loginRequest.setEmail("30billiongang@gmail.com");
        artistService.login(loginRequest);
    }
    @Test
    public void testThatArtistCanDisplayArtToTheTheArtGalleryAndArtRepositoryIncreaseToOne(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("Davido");
        registerRequest.setPassword("DavidOBO");
        registerRequest.setEmail("30billiongang@gmail.com");
        artistService.register(registerRequest);
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("Davido");
        loginRequest.setPassword("DavidOBO");
        loginRequest.setEmail("30billiongang@gmail.com");
        artistService.login(loginRequest);
        DisplayArtRequest displayArtRequest = new DisplayArtRequest();
        displayArtRequest.setArtistUsername("Davido");
        displayArtRequest.setEmail("30billiongang@gmail.com");
        displayArtRequest.setArtName("Monkey on the tree");
        displayArtRequest.setDescription("A paint picture which describes a monkey is on the tree");
        displayArtRequest.setAmount(BigDecimal.valueOf(3000));
        artistService.displayArt(displayArtRequest);
        assertEquals(1, artRepository.count());
    }
    @Test
    public void testThatWhenYouRegisterTwoArtistAndOneOfTheArtistDisplayAnArtItOnlyBelongsToThatArtist(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("Davido");
        registerRequest.setPassword("DavidOBO");
        registerRequest.setEmail("30billiongang@gmail.com");
        artistService.register(registerRequest);
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("Davido");
        loginRequest.setPassword("DavidOBO");
        loginRequest.setEmail("30billiongang@gmail.com");
        artistService.login(loginRequest);
        DisplayArtRequest displayArtRequest = new DisplayArtRequest();
        displayArtRequest.setEmail("30billiongang@gmail.com");
        displayArtRequest.setArtistUsername("Davido");
        displayArtRequest.setArtName("Monkey on the tree");
        displayArtRequest.setDescription("A paint picture which descibes a monkey is on the tree");
        displayArtRequest.setAmount(BigDecimal.valueOf(3000));
        artistService.displayArt(displayArtRequest);
        assertEquals(1, artistService.findAllArt("Davido","30billiongang@gmail.com").size());

    }
    @Test
    public void testThatArtistCanFindTheArtThatBelongToThem(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("Samantha");
        registerRequest.setPassword("Beloved453");
        registerRequest.setEmail("samanthabeloved@gmail.com");
        artistService.register(registerRequest);
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("Samantha");
        loginRequest.setPassword("Beloved453");
        loginRequest.setEmail("samanthabeloved77@gmail.com");
        artistService.login(loginRequest);
        DisplayArtRequest displayArtRequest = new DisplayArtRequest();
        displayArtRequest.setEmail("samanthabeloved77@gmail.com");
        displayArtRequest.setArtistUsername("Samatha");
        displayArtRequest.setArtName("Monkey on the tree");
        displayArtRequest.setDescription("A paint picture which is monkey is on the tree");
        displayArtRequest.setAmount(BigDecimal.valueOf(3000));
        artistService.displayArt(displayArtRequest);
        DisplayArtRequest displayArtRequest1 = new DisplayArtRequest();
        displayArtRequest1.setEmail("samanthabeloved77@gmail.com");
        displayArtRequest1.setArtistUsername("Samantha");
        displayArtRequest1.setArtName("The theme team");
        displayArtRequest1.setDescription("A paint picture that endorse team");
        displayArtRequest1.setAmount(BigDecimal.valueOf(2500));
        artistService.displayArt(displayArtRequest1);
        DisplayArtRequest displayArtRequest2 = new DisplayArtRequest();
        displayArtRequest2.setEmail("samanthabeloved77@gmail.com");
        displayArtRequest2.setArtistUsername("Samantha");
        displayArtRequest2.setArtName("Merge peg");
        displayArtRequest2.setDescription("A paint picture reference collaboration");
        displayArtRequest2.setAmount(BigDecimal.valueOf(4000));

        Art artToBeRemoved = artistService.displayArt(displayArtRequest2);
        RemoveAArtRequest request = new RemoveAArtRequest();
        request.setArtId(artToBeRemoved.getId());
        request.setEmail(registerRequest.getEmail());
        artistService.removeAArt(request);
        assertEquals(2,artistService.findAllArt(registerRequest.getUsername(),registerRequest.getEmail()).size());
    }
    @Test
    public void testThatUserCanFindAArtBelongToTheUser(){
        FindAArtRequest findAArtRequest = new FindAArtRequest();
        findAArtRequest.setEmail("ArtOfPiccaso@gmail.com");
        findAArtRequest.setArtId("1L");
        assertThrows(ArtNotFoundException.class, () -> artistService.findAArt(findAArtRequest));
    }
    @Test
    public void testThatUserCanRemoveArtBelongingToUser(){
        RemoveAArtRequest removeAArtRequest = new RemoveAArtRequest();
        removeAArtRequest.setEmail("CanvasSea846@gmail.com");
        removeAArtRequest.setArtId("1L");
        artistService.removeAArt(removeAArtRequest);
        FindAArtRequest findAArtRequest = new FindAArtRequest();
        findAArtRequest.setEmail("CanvasSea846@gmail.com");
        findAArtRequest.setArtId("1L");
        assertThrows(ArtNotFoundException.class, () -> artistService.findAArt(findAArtRequest));
    }

}
