package com.example.library_api.controllers;
import com.example.library_api.security.AuthService;
import com.example.library_api.security.LoginResponse;
import com.example.library_api.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
@RestController
    @RequestMapping("/auth")
    public class AuthController {

        @Autowired
        private AuthService authService;


        @Autowired
        private JwtUtil jwtUtil;

        // Эндпоинт для логина и получения токенов
        @PostMapping("/login")
        public ResponseEntity<LoginResponse> login(@RequestParam String username, @RequestParam String password) {
            System.out.println("Received login request: username=" + username + ", password=" + password);
            LoginResponse loginResponse = authService.login(username, password);
            return ResponseEntity.ok(loginResponse);  // Возвращаем ответ с access и refresh токенами
        }

        // Эндпоинт для получения нового access токена по refresh токену
        @PostMapping("/refresh-token")
        public ResponseEntity<LoginResponse> refreshToken(@RequestParam String refreshToken) {
            String username = jwtUtil.extractUsername(refreshToken);

            if (username != null && jwtUtil.validateToken(refreshToken, username)) {
                String newAccessToken = jwtUtil.generateToken(username);
                String newRefreshToken = jwtUtil.generateRefreshToken(username);
                LoginResponse loginResponse = new LoginResponse(newAccessToken, newRefreshToken);
                return ResponseEntity.ok(loginResponse);  // Возвращаем новые токены
            } else {
                return ResponseEntity.status(401).body(null);  // Неверный refresh токен
            }
        }
    }
