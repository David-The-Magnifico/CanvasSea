package africa.semicolon.CanvasSea.Data.Model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Document("Art")
public class Art {
    @Id
    private String id;
    private String name;
    private String description;
    private LocalDate dateUploaded = LocalDate.now();
    private boolean isPublished = false;
    private BigDecimal price;
    private Artist artist;
}
