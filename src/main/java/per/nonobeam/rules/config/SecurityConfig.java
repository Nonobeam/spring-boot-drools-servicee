package per.nonobeam.rules.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;

@Slf4j
@Configuration
@EnableWebSecurity(debug = false)
@RequiredArgsConstructor
public class SecurityConfig {

  private final String[] ALLOW_ENDPOINTS = {
    "/v2/api-docs/**",
    "/v3/api-docs",
    "/v3/**",
    "/swagger-ui/**",
    "/swagger-ui.html",
    "/swagger-resources",
    "/swagger-resources/**",
    "/swagger-ui/index.html#/**",
    "/swagger-ui/index.html/**",
    "/actuator/**",
    "/favicon.ico",
    "/login",
    "/index.html",
    "/assets/**",
    "/vite.svg",
    "/api/auth/**",
    "/api/auth/login",
    "/error",
    "/**"
  };

  @Bean
  public SecurityFilterChain defaultSecuredFilterChain(
      HttpSecurity http,
      @Qualifier("corsConfigurationSource") CorsConfigurationSource corsConfigurationSource)
      throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
        .cors(cors -> cors.configurationSource(corsConfigurationSource))
        .authorizeHttpRequests(
            auth ->
                auth.requestMatchers(ALLOW_ENDPOINTS)
                    .permitAll());

    return http.build();
  }
}
