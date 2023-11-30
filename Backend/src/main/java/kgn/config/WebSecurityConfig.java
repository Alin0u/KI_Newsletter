package kgn.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * Configuration class for web security settings.
 * Sets up the security filter chain and in-memory user details.
 */
@Configuration
@EnableWebSecurity
// TODO: delete when login-service properly configured
@PropertySource("classpath:application-secure.properties") // for developing only (passwords and usernames are in application-secure.properties)
public class WebSecurityConfig implements WebMvcConfigurer {
    @Value("${user.username}")
    private String userUsername;

    @Value("${user.password}")
    private String userPassword;

    @Value("${admin.username}")
    private String adminUsername;

    @Value("${admin.password}")
    private String adminPassword;

    /**
     * This method sets up the CORS configuration for the entire application by allowing requests
     * from specified origins and methods.
     *
     * @param registry the {@link CorsRegistry} to which CORS mappings are to be added. This is used
     *                 to configure CORS settings for the application.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // TODO: tighten for security reasons
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:4200") // TODO: change to url when installed
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // TODO: change to GET & POST
                .allowedHeaders("*") // TODO: "Content-Type", "Authorization"
                // TODO: .exposedHeaders("Custom-Header1", "Custom-Header2")
                .allowCredentials(true);
                // TODO: .maxAge(1800); -> 30 Min
    }

    /**
     * Defines the security filter chain.
     * Configures access to various URLs and sets up login and logout.
     *
     * @param http HttpSecurity
     * @return SecurityFilterChain
     * @throws Exception if an error occurs
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/", "/home","/api/text-generation/generate","/api/mail/send").permitAll()
                        .anyRequest().authenticated()
                )
                .cors().and()
                .cors(withDefaults())
                .csrf().disable()
                .formLogin().disable() // TODO: configure token-based auth.
                .logout((logout) -> logout.permitAll())
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        return http.build();
    }

    /**
     * In-memory user details service configuration.
     * Populates the user details from properties.
     *
     * @return UserDetailsService
     */
    @Bean
    public UserDetailsService userDetailsService() {
        // TODO: configure the login service properly
        UserDetails user =
                User.withDefaultPasswordEncoder()
                        .username(userUsername)
                        .password(userPassword)
                        .roles("USER")
                        .build();

        UserDetails admin =
                User.withDefaultPasswordEncoder()
                        .username(adminUsername)
                        .password(adminPassword)
                        .roles("ADMIN")
                        .build();

        return new InMemoryUserDetailsManager(user, admin);
    }
}