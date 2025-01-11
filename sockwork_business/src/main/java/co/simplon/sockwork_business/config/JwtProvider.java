package co.simplon.sockwork_business.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;


import java.time.Instant;
import java.util.List;
import java.util.Set;

public class JwtProvider {

    private final Algorithm algorithm;
    private final long expiration;
    private final String issuer;

    JwtProvider(Algorithm algorithm, long expiration, String issuer) {
        this.algorithm = algorithm;
        this.expiration = expiration;
        this.issuer = issuer;
    }

    public String create(String subject, Set<String> rolesList) {
        Instant issuedAt = Instant.now();
        List<String> toRoleslist = rolesList.stream().toList();
        JWTCreator.Builder builder = JWT.create().withIssuedAt(issuedAt)
                .withSubject(subject).withIssuer(issuer).withClaim("roles", toRoleslist);
        if(expiration > -1) {
            Instant expiresAt = issuedAt.plusSeconds(expiration);
            builder.withExpiresAt(expiresAt);
        }
        return builder.sign(algorithm);
    }
}
