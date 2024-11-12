package com.example.library_api.repositories;

import com.example.library_api.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository  extends JpaRepository <Employee, Long> {
    Optional<Employee> findByLogin(String login);
}
