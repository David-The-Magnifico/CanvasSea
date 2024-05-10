package africa.semicolon.CanvasSea.DTOs.Request;

import lombok.Data;
import org.bson.types.Binary;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Data
public class DisplayArtRequest {
    private String artName;
    private String description;
    private BigDecimal amount;
    private String artistUsername;
    private String email;
    private Binary image;
    private String contentType;
    private MultipartFile fileToUpload;


}