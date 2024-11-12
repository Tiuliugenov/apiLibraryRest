package com.example.library_api.services.impl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import com.example.library_api.entities.Author;
import com.example.library_api.entities.Reader;
import com.example.library_api.entities.TransactionBook;
import com.example.library_api.repositories.ReaderRepository;
import com.example.library_api.repositories.TransactionBookRepository;
import com.example.library_api.services.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service

public class ReaderServiceImpl implements ReaderService {
    @Autowired
    private ReaderRepository readerRepository;
    @Autowired
    private TransactionBookRepository transactionBookRepository;
    @Override
    public Reader getMostPopularReader() {
       // Пример: выбираем читателя с наибольшим количеством транзакций

        Pageable pageable = PageRequest.of(0, 1);
       List<Reader> topReader=transactionBookRepository.findTopReader(pageable);
        return topReader.isEmpty() ? null : topReader.get(0);
    }

    @Override
    public List <Reader> getReadersSortedByUnreturnedBooks() {
        List<Object[]> results = transactionBookRepository.findReadersSortedByUnreturnedBooks();
        List<Reader> readers = new ArrayList<>();
        for (Object[] result : results) {
            Reader reader = new Reader();
            reader.setId((Long) result[0]);
            reader.setDateOfBirth((LocalDate) result[1]);
            reader.setGender((String) result[2]);
            reader.setName((String) result[3]);
            reader.setPhoneNumber((int) result[4]);
            reader.setSurname((String) result[5]);
            readers.add(reader);
        }
        return readers;
    }
}