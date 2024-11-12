package com.example.library_api.services;

import com.example.library_api.entities.Reader;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface ReaderService {
    Reader getMostPopularReader();
    List<Reader> getReadersSortedByUnreturnedBooks();
}
