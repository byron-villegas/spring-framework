package cl.springframework.configuration;

import cl.springframework.filter.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfiguration {
    private final SecurityConfiguration securityConfiguration;
    private final JwtTokenFilter jwtTokenFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http = http.cors().and().csrf().disable(); // Enable CORS and disable CSRF

        http.headers().frameOptions().disable();

        // Set unauthorized requests exception handler
        http = http
                .exceptionHandling()
                .authenticationEntryPoint((request, response, ex) -> response.sendError(HttpStatus.UNAUTHORIZED.value(), ex.getMessage()))
                .and();

        // Set permissions on endpoints
        http
                .authorizeRequests()
                .antMatchers(securityConfiguration.getWhiteListUrls().toArray(new String[0]))
                .permitAll()
                .anyRequest()
                .authenticated();

        // Add JWT token filter
        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // Used by Spring Security if CORS is enabled.
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();

        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(securityConfiguration.getCors().isAllowCredentials());
        corsConfiguration.addAllowedOrigin(securityConfiguration.getCors().getAllowedOrigin());
        corsConfiguration.addAllowedHeader(securityConfiguration.getCors().getAllowedHeader());
        corsConfiguration.addAllowedMethod(securityConfiguration.getCors().getAllowedMethod());

        urlBasedCorsConfigurationSource.registerCorsConfiguration(securityConfiguration.getCors().getBasePath(), corsConfiguration);

        return new CorsFilter(urlBasedCorsConfigurationSource);
    }
}