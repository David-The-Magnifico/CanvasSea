package africa.semicolon.CanvasSea.Data.Model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.annotation.CreatedDate;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@Document(collection = "paystack_payment")
public class PaymentPaystack {

    @Id
    private String id;

    @DBRef
    private Artist artist;
    @DBRef
    private Buyer buyer;

    private String reference;
    private BigDecimal amount;
    private String gatewayResponse;
    private String paidAt;
    private String createdAt;
    private String channel;
    private String currency;
    private String ipAddress;
    private PricingPlanType planType;

    @CreatedDate
    private Date createdOn;

}
