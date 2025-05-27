package com.example.bankcards.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.bankcards.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class JwtUtil {

    @Value("${token.signing.key}")
    private String key;

    public String generateToken(UserDetails userDetails) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(key);

            User user = (User) userDetails;
            return JWT.create()
                    .withSubject(user.getUsername())
                    .withClaim("id",user.getId())
                    .withClaim("email",user.getEmail())
                    .withClaim("role",user.getRole().name())
                    .withIssuer("auth0")
                    .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))
                    .sign(algorithm);

        } catch (JWTCreationException exception){
            log.error("JWT generation failed", exception);
            throw new JWTCreationException("JWT generation failed",exception);
        }
    }

    public String getUsernameFromToken(String token) {
        return JWT.decode(token).getSubject();
    }

    public boolean validateJwtToken(String token, UserDetails userDetails) {
        try {
            User user = (User) userDetails;

            Algorithm algorithm = Algorithm.HMAC256(key);
            JWTVerifier verifier = JWT.require(algorithm)
                .withClaim("id",user.getId())
                .withClaim("email",user.getEmail())
                .withClaim("role",user.getRole().name())
                .withIssuer("auth0")
                .build();

            verifier.verify(token);
            return true;

        } catch (JWTVerificationException exception){
            log.error("JWT verification failed", exception);
            throw new JWTVerificationException("JWT verification failed",exception);
        }
    }
}
