package com.example.library_api.services;

import com.example.library_api.entities.Author;
import com.example.library_api.entities.TransactionBook;

import java.time.LocalDate;

public interface TransactionService {
    TransactionBook performTransaction(Long readerId, Long bookId, String operationType);
    Author getMostPopularAuthor (LocalDate startDate, LocalDate endDate);
}
