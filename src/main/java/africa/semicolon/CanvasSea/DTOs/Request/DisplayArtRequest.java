package africa.semicolon.CanvasSea.DTOs.Request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DisplayArtRequest {
    private String email;
    private String artName;
    private String description;
    private BigDecimal amount;
    private String artistUsername;
}