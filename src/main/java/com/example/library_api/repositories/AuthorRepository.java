package com.example.library_api.repositories;

import com.example.library_api.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository <Author, Long> {
    Author findByNameAndSurname(String name, String surname);
}
