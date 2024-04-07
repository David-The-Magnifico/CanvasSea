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
            throw new BuyerExistException("User already exist");
        validations(registerRequest);
        Buyer buyer = buyerMapper(registerRequest);
        return buyerRepository.save(buyer);
    }

    @Override
    public void login(LoginRequest loginRequest) {
        Optional<Buyer> foundBuyer = buyerRepository.findByUsername(loginRequest.getUsername());
        if (!checkIfBuyerExist(loginRequest.getUsername(), loginRequest.getEmail())) {
            throw new BuyerExistException("Invalid details");
        }
        if (!foundBuyer.get().getUsername().equalsIgnoreCase(loginRequest.getUsername())) {
            throw new InvalidDetailsException("Details entered are invalid");
        }
        if (!foundBuyer.get().getPassword().equalsIgnoreCase(loginRequest.getPassword())) {
            throw new InvalidDetailsException("Details entered are invalid");
        }
        if (!foundBuyer.get().getEmail().equalsIgnoreCase(loginRequest.getEmail())) {
            throw new InvalidDetailsException("Details entered are invalid");
        }
        foundBuyer.get().setEnable(false);
        buyerRepository.save(foundBuyer.get());
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
        return buyerRepository.findByUsername(buyerName).isPresent() && buyerRepository.findByEmail(email).isPresent();
    }

    @Override
    public List<Art> viewAllPublishedArt(String buyerName, String email) {
        ArrayList<Art> publishedArts = new ArrayList<>();
        validateBuyer(email);
        for (Art art : artService.findAllArt()) {
            if (art.isPublished()) {
                publishedArts.add(art);
            }
        }

        return publishedArts;

    }


    private void validateBuyer(String email) {
        Optional<Buyer> buyer = buyerRepository.findByEmail(email);
        if (buyer.isEmpty()) {
            throw new BuyerExistException("Account does not exist");
        }
    }
}
