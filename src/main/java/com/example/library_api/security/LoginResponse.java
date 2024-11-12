package com.example.library_api.security;



public class LoginResponse {

        private String accessToken;
        private String refreshToken;

        // Конструктор
        public LoginResponse(String accessToken, String refreshToken) {
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
        }

        // Геттеры
        public String getAccessToken() {
            return accessToken;
        }

        public String getRefreshToken() {
            return refreshToken;
        }
    }

