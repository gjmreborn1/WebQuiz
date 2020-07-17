package com.gjm.webquizengine;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.gjm.webquizengine.user.error_handling.JwtSecurityException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtils {
    @Value("${jwt_secret}")
    private String jwtSecret;

    public String generateToken(String username) {
        return JWT.create()
                .withIssuer("auth0")
                .withSubject(username)
                .sign(Algorithm.HMAC256(jwtSecret));
    }

    public String decodeToken(String token) {
        return JWT.require(Algorithm.HMAC256(jwtSecret))
                .withIssuer("auth0")
                .build()
                .verify(token)
                .getSubject();
    }

    public void validateToken(String token) {
        if(token == null || token.isEmpty() || token.isBlank()) {
            throw new JwtSecurityException();
        }

        try {
            JWT.require(Algorithm.HMAC256(jwtSecret))
                    .withIssuer("auth0")
                    .build()
                    .verify(token);
        } catch(JWTVerificationException exc) {
            throw new JwtSecurityException();
        }
    }
}
