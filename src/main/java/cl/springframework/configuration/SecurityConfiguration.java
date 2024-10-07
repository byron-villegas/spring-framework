package cl.springframework.configuration;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Data
@Configuration
@ConfigurationProperties("security")
public class SecurityConfiguration {
    private List<String> whiteListUrls;
    private Cors cors;

    @Getter
    @Setter
    static class Cors {
        private boolean allowCredentials;
        private String allowedOrigin;
        private String allowedHeader;
        private String AllowedMethod;
        private String basePath;
    }
}