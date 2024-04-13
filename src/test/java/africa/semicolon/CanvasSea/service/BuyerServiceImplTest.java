package africa.semicolon.CanvasSea.service;

import africa.semicolon.CanvasSea.DTOs.Request.*;
import africa.semicolon.CanvasSea.Data.Model.Art;
import africa.semicolon.CanvasSea.Data.Model.Artist;
import africa.semicolon.CanvasSea.Data.Repository.*;
import africa.semicolon.CanvasSea.Exceptions.*;
import africa.semicolon.CanvasSea.Services.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataMongoTest
public class BuyerServiceImplTest {
    @Autowired
    private AdminService adminService;
    @Autowired
    private ArtRepository artRepository;
    @Autowired
    private BuyerService buyerService;
    @Autowired
    private BuyerRepository buyerRepository;
    @Autowired
    private ArtistService artistService;
    @Autowired
    private ArtistRepository artistRepository;

    @AfterEach
    public void deleteBeforeTest(){
        buyerRepository.deleteAll();
        artRepository.deleteAll();
        artistRepository.deleteAll();
    }

    @Test
    public  void  testThatABuyer_CanRegisterWithWrongUsernameThrowsException(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("burna boy");
        registerRequest.setPassword("burna8906");
        registerRequest.setEmail("burnerboi765@gmail.com");
        assertThrows(InvalidUsernameException.class, () -> buyerService.register(registerRequest));
    }

    @Test
    public void testWhenBuyerRegisterWithWrongPasswordThrowsException(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("Precious");
        registerRequest.setPassword("ada");
        registerRequest.setEmail("joytim7277@gmail.com");
        assertThrows(InvalidPasswordException.class, () -> buyerService.register(registerRequest));
    }

    @Test
    public void testThatWhenBuyerRegisterWithWrongEmailThrowsException(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("Precious");
        registerRequest.setPassword("OlaPrecious");
        registerRequest.setEmail("joyt");
        assertThrows(InvalidEmailException.class, () -> buyerService.register(registerRequest));
    }


    @Test
    public void testThatBuyerCanRegisterAgainWithCorrectInfoReturnsArtistObjects(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("Precious");
        registerRequest.setPassword("OlaPrecious");
        registerRequest.setEmail("joy@gmail.com");
        buyerService.register(registerRequest);
        assertThrows(BuyerDoesNotExistException.class, () -> buyerService.register(registerRequest));
    }

    @Test
    public void testThatBuyerCan_RegisterAndLogin(){
        RegisterRequest registerRequest = new RegisterRequest();
        LoginRequest loginRequest = new LoginRequest();
        registerRequest.setUsername("Sandra");
        registerRequest.setPassword("Olaoluwajohn");
        registerRequest.setEmail("joy@gmail.com");
        buyerService.register(registerRequest) ;
        loginRequest.setUsername("Sandra");
        loginRequest.setPassword("Olaoluwajohn");
        loginRequest.setEmail("joy@gmail.com");
        buyerService.login(loginRequest);
    }

    @Test
    public void testThatBuyerWhenWantToPurchaseAArtWhichDoesNotExistThrowsException(){
        RegisterRequest registerRequest = new RegisterRequest();
        LoginRequest loginRequest = new LoginRequest();
        registerRequest.setUsername("Sandra");
        registerRequest.setPassword("Olaoluwajohn");
        registerRequest.setEmail("joy@gmail.com");
        buyerService.register(registerRequest) ;
        loginRequest.setUsername("Sandra");
        loginRequest.setPassword("Olaoluwajohn");
        loginRequest.setEmail("joy@gmail.com");
        buyerService.login(loginRequest);
        PurchaseArtRequest purchaseArtRequest = new PurchaseArtRequest();
        purchaseArtRequest.setBuyerUsername("Sandra");
        purchaseArtRequest.setBuyerEmail(registerRequest.getEmail());
        purchaseArtRequest.setArtId("1L");
        purchaseArtRequest.setAmount(BigDecimal.valueOf(2000));
        assertThrows(ArtNotFoundException.class, () -> buyerService.purchase(purchaseArtRequest));
    }
    @Test
    public void testThatABuyerWhenItBuyAArtItDeleteTheArtOnTheRepository(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("Sandra");
        registerRequest.setPassword("Olaoluwajohn");
        registerRequest.setEmail("deborahdelighted5@gmail.com");
        Artist artist = artistService.register(registerRequest);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("Sandra");
        loginRequest.setPassword("Olaoluwajohn");
        loginRequest.setEmail("deborahdelighted5@gmail.com");
        artistService.login(loginRequest);

        DisplayArtRequest displayArtRequest = new DisplayArtRequest();
        displayArtRequest.setArtistUsername("Sandra");
        displayArtRequest.setArtName("Monkey on the tree");
        displayArtRequest.setEmail(registerRequest.getEmail());
        displayArtRequest.setDescription("A paint picture which is monkey is on the tree");
        displayArtRequest.setAmount(BigDecimal.valueOf(3000));
        Art registerArt = artistService.displayArt(displayArtRequest);

        AdminRequest adminRequest = new AdminRequest();
        adminRequest.setUsername("Admin");
        adminRequest.setPassword("1234567");

        UploadRequest uploadRequest = new UploadRequest();
        uploadRequest.setArtId(registerArt.getId());
        uploadRequest.setEmail(artist.getEmail());
        registerArt = adminService.uploadArt(adminRequest, uploadRequest);

        RegisterRequest registerRequest1 = new RegisterRequest();
        registerRequest1.setUsername("Sandra");
        registerRequest1.setPassword("Olaoluwajohn");
        registerRequest1.setEmail("joy@gmail.com");
        buyerService.register(registerRequest1);

        LoginRequest loginRequest1 = new LoginRequest();
        loginRequest1.setUsername(registerRequest1.getUsername());
        loginRequest1.setPassword(registerRequest1.getPassword());
        loginRequest1.setEmail(registerRequest1.getEmail());
        buyerService.login(loginRequest1);

        PurchaseArtRequest purchaseArtRequest = new PurchaseArtRequest();
        purchaseArtRequest.setBuyerUsername("Sandra");
        purchaseArtRequest.setBuyerEmail(registerRequest1.getEmail());
        purchaseArtRequest.setArtId(registerArt.getId());
        purchaseArtRequest.setAmount(BigDecimal.valueOf(3000.00));
        assertEquals(1, artRepository.count());
        buyerService.purchase(purchaseArtRequest);
        assertEquals(0, artRepository.count());
        assertThrows(ArtNotFoundException.class, ()-> buyerService.purchase(purchaseArtRequest));
    }
    @Test
    public void testThatBuyerCanViewAllArtThatArePublished() {
        RegisterRequest registerRequest = new RegisterRequest();
        LoginRequest loginRequest = new LoginRequest();
        registerRequest.setUsername("Sandra");
        registerRequest.setPassword("Olaoluwajohn");
        registerRequest.setEmail("joy@gmail.com");
        buyerService.register(registerRequest);
        loginRequest.setUsername("Sandra");
        loginRequest.setPassword("Olaoluwajohn");
        loginRequest.setEmail("joy@gmail.com");
        buyerService.login(loginRequest);
        RegisterRequest registerRequestArtist = new RegisterRequest();
        registerRequestArtist.setUsername("Iyanu");
        registerRequestArtist.setPassword("Olaoluwajohn");
        registerRequestArtist.setEmail("deborahdelighted5@gmail.com");
        Artist artist = artistService.register(registerRequestArtist);
        LoginRequest loginRequestArtist = new LoginRequest();
        loginRequestArtist.setUsername("Iyanu");
        loginRequestArtist.setPassword("Olaoluwajohn");
        loginRequestArtist.setEmail("deborahdelighted5@gmail.com");
        artistService.login(loginRequestArtist);
        DisplayArtRequest displayArtRequest = new DisplayArtRequest();
        displayArtRequest.setArtistUsername("Iyanu");
        displayArtRequest.setEmail("deborahdelighted5@gmail.com");
        displayArtRequest.setArtName("Monkey on the tree");
        displayArtRequest.setDescription("A paint picture which is monkey is on the tree");
        displayArtRequest.setAmount(BigDecimal.valueOf(3000));
        Art art = artistService.displayArt(displayArtRequest);
        AdminRequest adminRequest = new AdminRequest();
        adminRequest.setUsername("Admin");
        adminRequest.setPassword("admin12");
        UploadRequest uploadRequest = new UploadRequest();
        uploadRequest.setArtId(art.getId());
        uploadRequest.setEmail("deborahdelighted5@gmail.com");
        adminService.uploadArt(adminRequest,uploadRequest);
        DisplayArtRequest displayArtRequest1 = new DisplayArtRequest();
        displayArtRequest1.setEmail("deborahdelighted5@gmail.com");
        displayArtRequest1.setArtistUsername("Iyanu");
        displayArtRequest1.setArtName("The theme team");
        displayArtRequest1.setDescription("A paint picture that endorse team");
        displayArtRequest1.setAmount(BigDecimal.valueOf(2500));
        Art art1 = artistService.displayArt(displayArtRequest1);
        UploadRequest uploadRequest1 = new UploadRequest();
        uploadRequest1.setArtId(art1.getId());
        uploadRequest1.setEmail("deborahdelighted5@gmail.com");
        adminService.uploadArt(adminRequest,uploadRequest1);
        DisplayArtRequest displayArtRequest2 = new DisplayArtRequest();
        displayArtRequest2.setEmail("deborahdelighted5@gmail.com");
        displayArtRequest2.setArtistUsername("Iyanu");
        displayArtRequest2.setArtName("Merge peg");
        displayArtRequest2.setDescription("A paint picture reference collaboration");
        displayArtRequest2.setAmount(BigDecimal.valueOf(4000));
        Art art2 = artistService.displayArt(displayArtRequest2);
        UploadRequest uploadRequest2 = new UploadRequest();
        uploadRequest2.setArtId(art2.getId());
        uploadRequest2.setEmail("deborahdelighted5@gmail.com");
        adminService.uploadArt(adminRequest,uploadRequest2);
        assertEquals(3, artistService.findAllArt("Iyanu","deborahdelighted5@gmail.com").size());
        assertEquals(3, buyerService.viewAllPublishedArt("Sandra","joy@gmail.com").size());
    }

}
