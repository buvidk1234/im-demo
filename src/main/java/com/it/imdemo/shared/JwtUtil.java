package com.it.imdemo.shared;


import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Date;


@Component
public class JwtUtil {

    SecretKey key = Jwts.SIG.HS256.key().build();
    private static final Duration TOKEN_VALIDITY = Duration.ofHours(2);

    public String generateToken(String id) {

        return Jwts.builder().subject(id)
                .signWith(key)
                .expiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY.toMillis()))
                .compact();
    }

    public String parseToken(String token) {
        try {

            return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().getSubject();

            //OK, we can trust this JWT

        } catch (JwtException e) {
            throw new RuntimeException("Invalid JWT token");
        }
    }
}
