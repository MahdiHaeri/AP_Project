package com.example.client.controllers;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class JWTController {
    private static String jwtKey;

    public static String getJwtKey() {
        return jwtKey;
    }

    public static void setJwtKey(String jwtKey) {
        JWTController.jwtKey = jwtKey;
    }

    public static void removeJwtKey() { JWTController.jwtKey = null; }

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
}