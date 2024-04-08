package africa.semicolon.CanvasSea.DTOs.Request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PurchaseArtRequest {
    private String buyerUsername;
    private String buyerEmail;
    private String artId;
    private BigDecimal amount;
}
