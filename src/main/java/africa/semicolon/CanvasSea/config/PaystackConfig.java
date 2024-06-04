package africa.semicolon.CanvasSea.config;

import africa.semicolon.CanvasSea.Data.Repository.ArtistRepository;
import africa.semicolon.CanvasSea.Data.Repository.PaystackPaymentRepositoryImpl;
import africa.semicolon.CanvasSea.Services.PaystackService;
import africa.semicolon.CanvasSea.Services.PaystackServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaystackConfig {

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private PaystackPaymentRepositoryImpl paystackPaymentRepositoryImpl;

    @Value("${paystack.secret.api.key}")
    private String paystackSecretKey;

    @Bean
    public PaystackService paystackService() {
        return new PaystackServiceImpl(paystackSecretKey, artistRepository, paystackPaymentRepositoryImpl);
    }
}