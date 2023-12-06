package kgn.config;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import kgn.util.KeyUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Files;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;


/**
 * WebSecurityConfig is a configuration class that sets up web security for the application.
 * It is marked with {@link Configuration} and {@link EnableWebSecurity}. It also implements
 * {@link WebMvcConfigurer} to provide additional MVC configuration.
 *
 * And it configures various aspects of security like CORS, JWT authentication and
 * authorization, and the security filter chain.
 */
@Configuration
@EnableWebSecurity
@PropertySource("classpath:application-secure.properties")
public class WebSecurityConfig implements WebMvcConfigurer {
//    @Value("${user.username}")
//    private String userUsername;
//
//    @Value("${user.password}")
//    private String userPassword;
//
//    @Value("${admin.username}")
//    private String adminUsername;
//
//    @Value("${admin.password}")
//    private String adminPassword;

    private final RSAPublicKey publicKey;
    private final RSAPrivateKey privateKey;

    /**
     * Constructor for WebSecurityConfig. It initializes RSA public and private keys
     *
     * @throws Exception if there is an issue in loading or parsing the keys.
     */
    public WebSecurityConfig() throws Exception {
        try {
            Resource publicKeyResource = new ClassPathResource("app.pub");
            Resource privateKeyResource = new ClassPathResource("app.key");

            String publicKeyContent = new String(Files.readAllBytes(publicKeyResource.getFile().toPath()));
            String privateKeyContent = new String(Files.readAllBytes(privateKeyResource.getFile().toPath()));

            this.publicKey = KeyUtils.convertPublicKey(publicKeyContent);
            this.privateKey = KeyUtils.convertPrivateKey(privateKeyContent);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize WebSecurityConfig", e);
        }
    }

    /**
     * Configures CORS settings for the application.
     *
     * @param registry the {@link CorsRegistry} to add mappings to.
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
     * This method configures HTTP security, setting up authorization, CSRF protection,
     * and other HTTP security settings.
     *
     * @param http        the {@link HttpSecurity} to configure.
     * @param jwtDecoder  the {@link JwtDecoder} for decoding JWTs.
     * @return {@link SecurityFilterChain} the configured security filter chain.
     * @throws Exception if an error occurs during the configuration.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtDecoder jwtDecoder) throws Exception {
        JwtAuthenticationProvider jwtAuthenticationProvider = new JwtAuthenticationProvider(jwtDecoder);
        jwtAuthenticationProvider.setJwtAuthenticationConverter(jwtAuthenticationConverter());
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/api/auth").permitAll()
                        .requestMatchers( "/home","/api/text-generation/generate").hasRole("ADMIN") // TODO: configure role-based login properly
                        .anyRequest().authenticated()
                )
                .csrf(csrf -> csrf.ignoringRequestMatchers("/api/auth"))
                .httpBasic(Customizer.withDefaults())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
                        .accessDeniedHandler(new BearerTokenAccessDeniedHandler())
                );
        return http.build();
    }

    /**
     * Creates an {@link AuthenticationManager} bean. The authentication manager is responsible
     * for processing authentication requests.
     *
     * @param authenticationConfiguration the {@link AuthenticationConfiguration} used to build the AuthenticationManager.
     * @return {@link AuthenticationManager} the authentication manager.
     * @throws Exception if an error occurs during creation.
     */
    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        System.out.println("authenticationManager");
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Creates a {@link JwtDecoder} bean for decoding JWTs.
     * This decoder uses the public RSA key configured in this class.
     *
     * @return {@link JwtDecoder} the JWT decoder.
     */
    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(this.publicKey).build();
    }

    /**
     * Creates a {@link JwtEncoder} bean for encoding JWTs.
     * This encoder uses the RSA key pair configured in this class.
     *
     * @return {@link JwtEncoder} the JWT encoder.
     */
    @Bean
    public JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(this.publicKey).privateKey(this.privateKey).build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }

    /**
     * Creates a {@link JwtAuthenticationConverter} bean. This converter is responsible for
     * converting a JWT to an authenticated principal with granted authorities.
     *
     * @return {@link JwtAuthenticationConverter} the JWT authentication converter.
     */
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthorityPrefix("");
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }

    /**
     * Creates a {@link PasswordEncoder} bean. This encoder is used for encoding
     * passwords in the application.
     *
     * @return {@link PasswordEncoder} the password encoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        System.out.println("passwordEncoder");
        return new BCryptPasswordEncoder();
    }


//    /**
//     * In-memory user details service configuration.
//     * Populates the user details from properties.
//     *
//     * @return UserDetailsService
//     */
//    @Bean
//    public UserDetailsService customUserDetailsService() {
//        UserDetails user =
//                User.withDefaultPasswordEncoder()
//                        .username(userUsername)
//                        .password(userPassword)
//                        .roles("USER")
//                        .build();
//
//        UserDetails admin =
//                User.withDefaultPasswordEncoder()
//                        .username(adminUsername)
//                        .password(adminPassword)
//                        .roles("ADMIN")
//                        .build();
//
//        return new InMemoryUserDetailsManager(user, admin);
//    }
}