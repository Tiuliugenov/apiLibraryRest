package com.example.library_api.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {
    private SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // Теперь используйте secretKey для подписи JWT
    // Генерация токена
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 600000)) // 10 минут жизни
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    // Проверка истечения токена
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Извлечение даты истечения
    public Date extractExpiration(String token) {
        return parseClaims(token).getExpiration();
    }

    // Извлечение имени пользователя из токена
    public String extractUsername(String token) {
        return parseClaims(token).getSubject();
    }

    // Извлечение всех Claims из токена
    private Claims parseClaims(String token) {
        return Jwts.parser()  // Используем старый метод parser() для версии 0.12.0
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    // Проверка валидности токена
    public boolean validateToken(String token, String username) {
        return (username.equals(extractUsername(token)) && !isTokenExpired(token));
    }

    public String generateRefreshToken(String username) {
        return Jwts.builder()
                .setSubject(username)  // Устанавливаем имя пользователя как subject токена
                .setIssuedAt(new Date())  // Устанавливаем дату создания токена
                .setExpiration(new Date(System.currentTimeMillis() + 1800000))  // Устанавливаем время жизни токена (30 минут)
                .signWith(SignatureAlgorithm.HS256, secretKey)  // Используем секретный ключ для подписи
                .compact();  // Создаем токен
    }
}
