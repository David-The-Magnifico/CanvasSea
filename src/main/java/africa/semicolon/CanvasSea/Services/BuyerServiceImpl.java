package africa.semicolon.CanvasSea.Services;

import africa.semicolon.CanvasSea.DTOs.Request.*;
import africa.semicolon.CanvasSea.Data.Model.Art;
import africa.semicolon.CanvasSea.Data.Model.Buyer;
import africa.semicolon.CanvasSea.Data.Repository.BuyerRepository;
import africa.semicolon.CanvasSea.Exceptions.*;
import africa.semicolon.CanvasSea.Utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static africa.semicolon.CanvasSea.Utils.Mapper.buyerMapper;

@Service
public class BuyerServiceImpl implements BuyerService {
    @Autowired
    private BuyerRepository buyerRepository;
    @Autowired
    private ArtService artService;

    @Override
    public Buyer register(RegisterRequest registerRequest) {
        if (checkIfBuyerExist(registerRequest.getUsername(), registerRequest.getEmail()))
            throw new BuyerDoesNotExistException("User already exists");
        validations(registerRequest);
        Buyer buyer = buyerMapper(registerRequest);
        return buyerRepository.save(buyer);
    }

    @Override
    public void login(LoginRequest loginRequest) {
        Buyer foundBuyer = buyerRepository.findByUsername(loginRequest.getUsername());
        if (foundBuyer == null) {
            throw new BuyerDoesNotExistException("Invalid details");
        }
        if (!foundBuyer.getUsername().equalsIgnoreCase(loginRequest.getUsername())
                || !foundBuyer.getPassword().equalsIgnoreCase(loginRequest.getPassword())
                || !foundBuyer.getEmail().equalsIgnoreCase(loginRequest.getEmail())) {
            throw new InvalidDetailsException("Details entered are invalid");
        }
        foundBuyer.setPresent(false);
        buyerRepository.save(foundBuyer);
    }

    @Override
    public void logout(LogoutRequest logoutRequest) {
        if (!checkIfBuyerExists(logoutRequest.getUsername()))
            throw new BuyerDoesNotExistException("Buyer does not exist");

        Optional<Buyer> foundBuyer = Optional.ofNullable(buyerRepository.findByUsername(logoutRequest.getUsername()));

        if (foundBuyer.isPresent() && foundBuyer.get().getUsername().equalsIgnoreCase(logoutRequest.getUsername())) {
            foundBuyer.get().setPresent(false);
            buyerRepository.save(foundBuyer.get());
        } else {
            throw new InvalidDetailsException("Incorrect Username");
        }
    }

    private boolean checkIfBuyerExists(String buyerUsername) {
        return buyerRepository.findByUsername(buyerUsername) != null;
    }

    @Override
    public void purchase(PurchaseArtRequest purchaseArtRequest) {
        Buyer buyer = buyerRepository.findByUsername(purchaseArtRequest.getBuyerUsername());
        if (buyer == null) {
            throw new BuyerDoesNotExistException("Buyer not found");
        }

        Art art = artService.findArtById(purchaseArtRequest.getArtId());
        if (art == null) {
            throw new ArtNotFoundException("Art not found");
        }

        if (art.isSold()) {
            throw new ArtNotFoundException("Art is sold out");
        }

        if (art.getPrice().compareTo(purchaseArtRequest.getAmount()) != 0) {
            throw new InsufficientAmountException("Invalid amount for the art");
        }

        if (buyer.getBalance().compareTo(art.getPrice()) < 0) {
            throw new InsufficientAmountException("Insufficient balance to purchase the art");
        }

        buyer.setBalance(buyer.getBalance().subtract(art.getPrice()));
        art.setSold(true);
        art.setBuyer(buyer);

        buyerRepository.save(buyer);
        artService.saveArt(art);
        System.out.println("Purchase successful for buyer: " + purchaseArtRequest.getBuyerUsername() + " for art ID: " + purchaseArtRequest.getArtId());
    }

    public void validations(RegisterRequest registerRequest) {
        if (!Validator.validateName(registerRequest.getUsername())) {
            throw new InvalidUsernameException("Invalid username");
        }
        if (!Validator.validatePassword(registerRequest.getPassword())) {
            throw new InvalidPasswordException("Invalid password");
        }
        if (!Validator.validateEmail(registerRequest.getEmail())) {
            throw new InvalidEmailException("Invalid Email");
        }
    }

    public boolean checkIfBuyerExist(String buyerName, String email) {
        return buyerRepository.findByUsername(buyerName) != null && buyerRepository.findByEmail(email) != null;
    }

    @Override
    public List<Art> viewAllAvailableArts(String buyerName, String email) {
        ArrayList<Art> publishedArts = new ArrayList<>();
        validateBuyer(email);
        for (Art art : artService.findAllArt()) {
            if (art.isAvailable()) {
                publishedArts.add(art);
            }
        }
        return publishedArts;
    }

    private void validateBuyer(String email) {
        Buyer buyer = buyerRepository.findByEmail(email);
        if (buyer == null) {
            throw new BuyerDoesNotExistException("Account does not exist");
        }
    }
}
