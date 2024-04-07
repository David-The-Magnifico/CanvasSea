package africa.semicolon.CanvasSea.DTOs.Request;

import lombok.Data;

@Data
public class RemoveArtistRequest {
    private String username;
    private String email;
    private String artId;
}
