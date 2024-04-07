package africa.semicolon.CanvasSea.Services;

import africa.semicolon.CanvasSea.DTOs.Request.LoginRequest;
import africa.semicolon.CanvasSea.DTOs.Request.RegisterRequest;
import africa.semicolon.CanvasSea.Data.Model.Art;
import africa.semicolon.CanvasSea.Data.Model.Buyer;

import java.util.List;

public interface BuyerService {
    List<Art> viewAllPublishedArt(String buyerName, String email);

    Buyer register(RegisterRequest registerRequest);

    void login(LoginRequest loginRequest);
}
