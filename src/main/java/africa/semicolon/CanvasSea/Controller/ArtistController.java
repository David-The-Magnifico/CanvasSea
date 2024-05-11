package africa.semicolon.CanvasSea.Controller;

import africa.semicolon.CanvasSea.DTOs.Request.*;
import africa.semicolon.CanvasSea.DTOs.Response.ApiResponse;
import africa.semicolon.CanvasSea.Data.Model.Art;
import africa.semicolon.CanvasSea.Services.AdminService;
import africa.semicolon.CanvasSea.Services.ArtistService;
import africa.semicolon.CanvasSea.Services.cloud.CloudServices;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/artists")
@RequiredArgsConstructor
public class ArtistController {

    private final ArtistService artistService;
    private final CloudServices cloudServices;


    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        try {
            artistService.register(registerRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body("Registration successful");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Registration failed: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login( @RequestBody LoginRequest LoginRequest) {
        try {
            artistService.login(LoginRequest);
            return ResponseEntity.ok("Login successful");
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed, : " + exception.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout( @RequestBody LogoutRequest LogoutRequest) {
        try {
            artistService.logout(LogoutRequest);
            return ResponseEntity.ok("Artist is Logged out successfully");
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Logout failed, : " + exception.getMessage());
        }
    }

    @PostMapping("/displayArt")
    public ResponseEntity<String> displayArt(@RequestBody DisplayArtRequest displayArtRequest) {
        try {
            artistService.displayArt(displayArtRequest);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Art displayed successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to display art: " + e.getMessage());
        }
    }

    @GetMapping("/findAllArt")
    public ResponseEntity<List<Art>> findAllArt(@RequestParam String username, @RequestParam String email) {
        List<Art> artList = artistService.findAllArt(username, email);
        return ResponseEntity.ok(artList);
    }

    @GetMapping("/findAArt")
    public ResponseEntity<?> findAArt(@RequestBody FindAArtRequest findAArtRequest) {
        try {
            Art foundArt = artistService.findAArt(findAArtRequest);
            return new ResponseEntity<>(new ApiResponse(true, foundArt), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Art not found: " + e.getMessage());
        }
    }

    @PostMapping("upload")
    public ResponseEntity<ApiResponse> upload(@RequestParam(value = "upload") MultipartFile file) {

            String url = cloudServices.uploadImage(file);
            return new ResponseEntity<> (new ApiResponse(true, url), HttpStatus.OK);
    }
}
