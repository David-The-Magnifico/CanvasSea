package africa.semicolon.CanvasSea.Data.Model;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Document("Admin")
public class Admin {
    @Id
    private String id;
    private String username;
    private String password;
    private String email;
    private boolean isEnable;
}
