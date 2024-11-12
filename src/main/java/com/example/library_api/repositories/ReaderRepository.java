package com.example.library_api.repositories;

import com.example.library_api.entities.Reader;
import com.example.library_api.entities.TransactionBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReaderRepository extends JpaRepository<Reader, Long> {
}
