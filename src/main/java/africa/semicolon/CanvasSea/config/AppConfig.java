package africa.semicolon.CanvasSea.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AppConfig {

    @Value("${cloud.api.name}")
    private String cloudApiName;

    @Value("${cloud.api.key}")
    private String cloudApiKey;

    @Value("${cloud.api.secret}")
    private String cloudApiSecret;

    public String getCloudApiName() {
        return cloudApiName;
    }

    public String getCloudApiKey() {
        return cloudApiKey;
    }

    public String getCloudApiSecret() {
        return cloudApiSecret;
    }
}
