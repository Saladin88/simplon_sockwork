package co.simplon.sockwork_business.config;

import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;

import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

@Configuration
public class WebConfig {

    @Value("${co.simplon.sockwork_business.cors}")
    private String origins;

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() { //function anonyme. Elle implémente une interface . de base on ne peut pas implementer une interface

            @Override
            public void addCorsMappings(CorsRegistry registry) {//Cette méthod existe dans l'interface. On utilise Override pour la réecrire
                registry.addMapping("/**").allowedMethods("GET", "POST", "PATCH", "PUT").allowedOrigins(origins);
            }
        };
    }

    //Authorization ressource service :

    @Value("${co.simplon.sockwork_business.secret_jwt}")
    private String secret;

    @Value("${co.simplon.sockwork_business.expiration_jwt}")
    private long expiration;
    @Value("${co.simplon.sockwork_business.issuer}")
    private String issuer; // qui est le producteur/générateur du token

    @Bean
    JwtProvider jwtProvider() {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return new JwtProvider(algorithm, expiration,issuer);
    }

    //Ressource service config :

    @Bean //objet reconnu et géré par spring
    JwtDecoder jwtDecoder() { // Tell Spring how to verify JWT signature
        SecretKey secretKey = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
        NimbusJwtDecoder decoder = NimbusJwtDecoder.withSecretKey(secretKey)
                .macAlgorithm(MacAlgorithm.HS256)
                .build();
        OAuth2TokenValidator<Jwt> auth = JwtValidators.createDefaultWithIssuer(issuer);
               // new JwtIssuerValidator(issuer);
        decoder.setJwtValidator(auth);
        return decoder;
    }


    @Bean // injection de dépendance
    //Change default behaviour
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf((csrf) -> csrf.disable())  // Désactive la protection CSRF (pas nécessaire avec JWT)
                .cors(Customizer.withDefaults())
//                .oauth2ResourceServer(oauth2 -> oauth2
//                        .jwt(jwt -> jwt.decoder(jwtDecoder()))  // Configurer le JwtDecoder pour valider les JWT
//                )
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(HttpMethod.POST,"/accounts","/accounts/log-in").anonymous())
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers(HttpMethod.GET,"accounts/with-role").hasRole("MGR")) //Authorities === privilèges
                .authorizeHttpRequests((auth) -> auth.anyRequest().authenticated())
                .oauth2ResourceServer((srv) -> srv.jwt(Customizer.withDefaults())  // Configurer le JwtDecoder pour valider les JWT

                )


//                .formLogin(formLogin -> formLogin
//                        .loginPage("/accounts/log")
//                        .loginProcessingUrl("/accounts/log")
//                        .permitAll(true)
//                )
;

        return http.build();
        // Default Spring behaviour: PoLP (no authorization at all)
        // Relies on JWT
        // authorize some requests or not
        // ???
    }

}
