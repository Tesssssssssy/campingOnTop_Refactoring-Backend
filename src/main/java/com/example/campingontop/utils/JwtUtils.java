package com.example.campingontop.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;

import java.security.Key;
import java.util.Date;

public class JwtUtils {
    @Value("${jwt.token.expired-time-ms}")
    public Integer expiredMs;

    public static String generateAccessToken(String email, String nickname, Long id, String key, Integer expiredMs) {
        Claims claims = Jwts.claims();
        claims.put("email", email);
        claims.put("nickname", nickname);
        claims.put("id", id);

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(getSignKey(key), SignatureAlgorithm.HS256)
                .compact();

        return token;
    }

    public static Key getSignKey(String secretKey) {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public static Boolean validate(String token, String key) {
        Date expireTime = extractAllClaims(token, key).getExpiration();
        Boolean result = expireTime.before(new Date(System.currentTimeMillis()));

        return !result;
    }

    public static String getMemberEmail(String token, String key) {
        return extractAllClaims(token, key).get("email", String.class);

    }

    public static Long getMemberId(String token, String key) {
        return extractAllClaims(token, key).get("id", Long.class);
    }

    public static String getMemberNickname(String token, String key) {
        return extractAllClaims(token, key).get("nickname", String.class);
    }

    public static Claims extractAllClaims(String token, String key) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey(key))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public static String replaceToken(String token) {
        if (token.startsWith("Bearer ")) {
            token = token.split(" ")[1];
        }

        return token;
    }
}
