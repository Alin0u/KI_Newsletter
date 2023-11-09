package kgn.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuration class for web security settings.
 * Sets up the security filter chain and in-memory user details.
 */
@Configuration
@EnableWebSecurity
// TODO: delete when login-service properly configured
@PropertySource("classpath:application-secure.properties") // for developing only (passwords and usernames are in application-secure.properties)
public class WebSecurityConfig {
    @Value("${user.username}")
    private String userUsername;

    @Value("${user.password}")
    private String userPassword;

    @Value("${admin.username}")
    private String adminUsername;

    @Value("${admin.password}")
    private String adminPassword;

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
                        .requestMatchers("/", "/home").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        //.loginPage("/login")
                        // TODO: configure login page
                        .permitAll()
                )
                .logout((logout) -> logout.permitAll());

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