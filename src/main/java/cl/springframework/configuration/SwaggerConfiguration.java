package cl.springframework.configuration;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("swagger")
public class SwaggerConfiguration {
    private String basePackage;
    private String title;
    private String description;
    private Contact contact;
    private String license;
    private String licenseUrl;
    private String version;

    @Getter
    @Setter
    static class Contact {
        private String name;
        private String url;
        private String email;
    }
}