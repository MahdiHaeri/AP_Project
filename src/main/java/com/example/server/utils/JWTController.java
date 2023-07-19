package com.example.server.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

public class JWTController {
    private static final long EXPIRATION_TIME = 3600000; // 1 hour

    // Use the Singleton pattern to ensure the same secret key is reused
    private static SecretKey secretKeyInstance;

    private static SecretKey getSecretKey() {
        if (secretKeyInstance == null) {
            secretKeyInstance = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        }
        return secretKeyInstance;
    }

    public static String generateJwtToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSecretKey())
                .compact();
    }

    public static String getUsernameFromJwtToken(String jwtToken) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSecretKey())
                    .build()
                    .parseClaimsJws(jwtToken)
                    .getBody();

            return claims.getSubject();
        } catch (ExpiredJwtException | MalformedJwtException | SignatureException | IllegalArgumentException ex) {
            // Handle different types of exceptions, e.g., log the error or return null/empty string
            return null;
        }
    }

    public static boolean validateJwtToken(String jwtToken) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSecretKey())
                    .build()
                    .parseClaimsJws(jwtToken);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String getJwtTokenFromHeader(String header) {
        return header.substring(7);
    }
}
