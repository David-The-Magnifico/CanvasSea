package africa.semicolon.CanvasSea.Data.Model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@Document("Buyer")
public class Buyer {
    @Id
    private String id;
    private String username;
    private String password;
    private boolean isLoggedIn = false;
    private boolean isPresent = false;
    private String email;
    public BigDecimal Balance;
}