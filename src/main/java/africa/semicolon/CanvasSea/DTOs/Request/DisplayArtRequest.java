package africa.semicolon.CanvasSea.DTOs.Request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;

@Data
public class DisplayArtRequest {
    private String artName;
    private String description;
    private BigDecimal amount;
    private String artistUsername;
    private String email;
    @Setter
    @Getter
    private MultipartFile image;
    
    public byte[] getImageBytes() throws IOException {
        try {

            return this.image.getBytes();
        }
        catch (IOException e) {
            throw new IOException("Kilosele gan gan!!");
        }
    }

}