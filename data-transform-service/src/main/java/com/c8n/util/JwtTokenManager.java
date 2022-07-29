package com.c8n.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class JwtTokenManager {

    @Value("${DHSECRETKEY}")
    private String secretKey;

    private final long expiresAt = 24*60*60*1000;
    private final String issuer = "com.c8n";
//    private Algorithm algorithm = Algorithm.HMAC256(secretKey);

    public Optional<String> generateToken(String userId){
        String token;

        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        token = JWT.create()
                .withAudience()
                .withClaim("userId", userId)
                .withIssuer(issuer)
                .withExpiresAt(new Date(System.currentTimeMillis() + expiresAt))
                .withIssuedAt(new Date())
                .sign(algorithm);

        return Optional.of(token);
    }

    public boolean validateToken(String token){
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        JWTVerifier verifier = JWT.require(algorithm).withIssuer(issuer).build();
        DecodedJWT decodedJWT;
        try {
            decodedJWT = verifier.verify(token);
        } catch (Exception e) {
            decodedJWT = null;
        }

        return decodedJWT != null;
    }

    public Optional<String> decodeUserId(String token){
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        JWTVerifier verifier = JWT.require(algorithm).withIssuer(issuer).build();
        DecodedJWT decodedJWT;

        try {
            decodedJWT = verifier.verify(token);
        } catch (Exception e) {
            decodedJWT = null;
        }

        if (decodedJWT == null)
            return Optional.empty();

        return Optional.of(decodedJWT.getClaim("userId").asString());
    }
}
