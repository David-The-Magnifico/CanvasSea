package africa.semicolon.CanvasSea.DTOs.Response;

import lombok.Data;
import africa.semicolon.CanvasSea.Data.Model.Artist;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class FindAArtResponse {
    private String id;
    private BigDecimal amount;
    private String artName;
    private String description;
    private LocalDate date;
    private String artistName;
}
