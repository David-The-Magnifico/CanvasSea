package africa.semicolon.CanvasSea.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


@Getter
@Configuration
public class AppConfig {

    @Value("${cloud.api.name}")
    private String cloudApiName;

    @Value("${cloud.api.key}")
    private String cloudApiKey;

    @Value("${cloud.api.secret}")
    private String cloudApiSecret;

}
