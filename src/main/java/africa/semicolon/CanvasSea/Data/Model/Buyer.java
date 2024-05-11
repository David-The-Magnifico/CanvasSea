package africa.semicolon.CanvasSea.Data.Model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Date;

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
    private BigDecimal balance;
    private String address;

    @CreatedDate
    private Date createdOn;

}