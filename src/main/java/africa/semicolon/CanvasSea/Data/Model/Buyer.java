package africa.semicolon.CanvasSea.Data.Model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("Buyer")
public class Buyer {
    @Id
    private String buyerId;
    private String username;
    private String password;
    private boolean isEnable;
    private String email;
}
