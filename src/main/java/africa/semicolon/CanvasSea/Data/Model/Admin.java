package africa.semicolon.CanvasSea.Data.Model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Data
@Document("Admin")
public class Admin {
    @Id
    private String adminId;
    private String username;
    private String password;
    private String email;
    private boolean isEnable;
}
