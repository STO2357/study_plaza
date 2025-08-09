package sto.study_plaza.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import sto.study_plaza.config.props.CorsProps;
import sto.study_plaza.config.props.SecurityProps;

import java.util.List;


@Configuration
public class SecurityConfig {

    private final CorsProps corsProps;
    private final SecurityProps securityProps;

    public SecurityConfig(CorsProps corsProps, SecurityProps securityProps) {
        this.corsProps = corsProps;
        this.securityProps = securityProps;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(corsProps.getAllowedOrigins());
        config.setAllowedMethods(corsProps.getAllowedMethods());
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()));

        if (!securityProps.isCsrfEnabled()) {
            http.csrf(csrf -> csrf.disable());
        }

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(securityProps.getPermitAllPatterns().toArray(new String[0])).permitAll()
                .anyRequest().authenticated()
        ).formLogin(login -> login.disable());

        return http.build();
    }
}