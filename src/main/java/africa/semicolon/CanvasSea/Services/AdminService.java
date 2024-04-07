package africa.semicolon.CanvasSea.Services;

import africa.semicolon.CanvasSea.DTOs.Request.*;
import africa.semicolon.CanvasSea.Data.Model.Art;

public interface AdminService {
    Art uploadArt(AdminRequest adminRequest, UploadRequest uploadRequest);
    void removeArtist(AdminRequest adminRequest, RemoveArtistRequest removeArtistRequest);
    void confirmAdmin(AdminRequest adminRequest, String email, String password);
}
