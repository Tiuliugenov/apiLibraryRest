package com.example.library_api.security;

import com.example.library_api.entities.Employee;

import com.example.library_api.repositories.EmployeeRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
@Service
public class AuthService {

    @Autowired
    PasswordEncoder passwordEncoder;
    private JwtUtil jwtUtil;  // Используем класс JwtUtil для генерации токенов4
    @Autowired
    private EmployeeRepository employeeRepository; // Репозиторий для работы с сотрудниками

    // Конструктор для внедрения зависимостей
    public AuthService(JwtUtil jwtUtil, EmployeeRepository employeeRepository) {
        this.jwtUtil = jwtUtil;
        this.employeeRepository = employeeRepository;
    }

    public LoginResponse login(String login, String password) {


        // Проверка сотрудника в базе данных (например, по имени пользователя и паролю)
        Employee employee = employeeRepository.findByLogin(login)

                .orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));
        System.out.println("Введенный пароль: " + password);
        System.out.println("Пароль в базе данных: " + employee.getPassword());

        if (!passwordEncoder.matches(password, employee.getPassword())) {
            throw new IllegalArgumentException("Invalid username or password");
        }

        // Генерация токенов
        String accessToken = jwtUtil.generateToken(login); // Токен на 10 минут
        String refreshToken = jwtUtil.generateRefreshToken(login); // Токен на 30 минут

        return new LoginResponse(accessToken, refreshToken);
    }
}
