package africa.semicolon.CanvasSea.Controller;

import africa.semicolon.CanvasSea.DTOs.Request.*;
import africa.semicolon.CanvasSea.DTOs.Response.*;
import africa.semicolon.CanvasSea.Services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AdminController {
    @Autowired
    private AdminService adminService;

    @PostMapping("Admin/upload-art")
    public ResponseEntity<?> uploadArt(@RequestBody AdminRequest adminRequest, UploadRequest uploadRequest){
        UploadResponse uploadResponse = new UploadResponse();

        try {
            adminService.uploadArt(adminRequest, uploadRequest);
            uploadResponse.setMessage("Art with Id " + uploadRequest.getArtId() + "displayed by artist with Id " + uploadRequest.getArtistId() + "has been uploaded successfully");
            return new ResponseEntity<>(new ApiResponse(true,uploadResponse), HttpStatus.ACCEPTED);
        }
        catch (Exception exception){
            uploadResponse.setMessage(exception.getMessage());
            return new ResponseEntity<>(new ApiResponse(false,uploadResponse),HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("Admin/remove-artist")
    public ResponseEntity<?> removeArtist(@RequestBody AdminRequest adminRequest, RemoveArtistRequest removeArtistRequest){
        ArtistResponse removeArtistResponse = new ArtistResponse();


