package com.example.server.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
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

    public static String getSubjectFromJwt(String jwt) {
        try {
            String[] jwtParts = jwt.split("\\.");

            if (jwtParts.length == 3) {
                String encodedPayload = jwtParts[1];
                byte[] decodedPayload = Base64.getUrlDecoder().decode(encodedPayload);
                String payloadJson = new String(decodedPayload, StandardCharsets.UTF_8);

                // Parse the payload JSON and retrieve the "sub" value
                // Assuming the payload JSON is in the format: {"sub": "username"}
                String sub = payloadJson.substring(payloadJson.indexOf("\"sub\":") + 7);
                sub = sub.substring(0, sub.indexOf("\""));

                return sub;
            } else {
                // Invalid JWT format
                throw new IllegalArgumentException("Invalid JWT format");
            }
        } catch (Exception e) {
            // Handle exception (e.g., invalid JWT format, decoding errors)
            e.printStackTrace();
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
