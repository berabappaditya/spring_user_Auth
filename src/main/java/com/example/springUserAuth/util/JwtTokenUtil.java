package com.example.springUserAuth.util;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.Keys;
import java.security.Key;


import com.example.springUserAuth.models.User;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenUtil {

    public static final long REFRESH_TOKEN_EXPIRATION = 24 * 60 * 60 * 1000;
    //    public static final long REFRESH_TOKEN_EXPIRATION = ;
    private final String JWT_SECRET = "Aditya004@11B";
    private final long ACCESS_TOKEN_EXPIRATION = 15 * 60 * 1000; // 15 minutes
    //private final long REFRESH_TOKEN_EXPIRATION = 24 * 60 * 60 * 1000; // 1 day

    public String generateAccessToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION))
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET)
                .compact();
    }

    public String generateRefreshToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION))
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).build();
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String getUsernameFromToken(String token) {
        Claims claims = (Claims) Jwts.parser()
                .setSigningKey(JWT_SECRET).build();
        return claims.getSubject();
    }
}

