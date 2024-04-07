package africa.semicolon.CanvasSea.DTOs.Request;

import lombok.Data;

@Data
public class UploadRequest {
    private String username;
    private String password;
    private String email;
    private String artId;
    private String artistId;
}
