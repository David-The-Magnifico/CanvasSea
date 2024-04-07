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

        
