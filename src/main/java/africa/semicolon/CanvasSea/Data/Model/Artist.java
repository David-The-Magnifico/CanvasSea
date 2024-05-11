package africa.semicolon.CanvasSea.Data.Model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.util.Date;
import java.util.List;

@Data
@Document("Artist")
public class Artist {
    @Id
    private String id;
    private String username;
    private String password;
    private boolean isLoggedIn = false;
    private boolean isPresent = false;
    private String email;
    private List<Art> artists;
    private String address;

    @CreatedDate
    private Date createdDate;

    public Artist get() {
        return (Artist) artists;
    }
}
