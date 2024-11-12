package com.example.library_api.repositories;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import com.example.library_api.entities.Reader;
import com.example.library_api.entities.TransactionBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.time.LocalDateTime;
import java.util.List;

public interface TransactionBookRepository extends JpaRepository <TransactionBook, Long> {
    List<TransactionBook> findAllByDateTimeBetween(LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT r FROM Reader r WHERE r.id IN (SELECT t.reader.id FROM TransactionBook t GROUP BY t.reader.id ORDER BY COUNT(t) DESC)")
    List<Reader> findTopReader(Pageable pageable);


    @Query("SELECT r.id, r.dateOfBirth, r.gender, r.name, r.phoneNumber, r.surname, COUNT(t) AS notReturnedCount " +
            "FROM TransactionBook t " +
            "JOIN t.reader r " +
            "WHERE t.typeOperation = 'issue' AND " +
            "NOT EXISTS (SELECT 1 FROM TransactionBook t2 " + // Corrected 'exists' to 'EXISTS' and fixed subquery
            "            WHERE t2.book = t.book AND t2.reader = t.reader AND t2.typeOperation = 'return') " + // Corrected placement and logic
            "GROUP BY r.id, r.dateOfBirth, r.gender, r.name, r.phoneNumber, r.surname " + // Corrected 'BY' to 'GROUP BY'

            "ORDER BY notReturnedCount DESC")
    List<Object[]> findReadersSortedByUnreturnedBooks();
}
