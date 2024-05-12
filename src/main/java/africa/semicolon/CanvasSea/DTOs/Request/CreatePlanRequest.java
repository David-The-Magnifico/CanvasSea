package africa.semicolon.CanvasSea.DTOs.Request;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.Digits;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "plans")
public class CreatePlanRequest {

    @Id
    private String id;

    @NotNull(message = "Plan name cannot be null")
    @JsonProperty("name")
    private String name;

    @NotNull(message = "Interval cannot be null")
    @JsonProperty("interval")
    private String interval;

    @NotNull(message = "Amount cannot be null")
    @JsonProperty("amount")
    @Digits(integer = 6, fraction = 2)
    private BigDecimal amount;
}