package com.example.library_api.services.impl;

import com.example.library_api.entities.Author;
import com.example.library_api.entities.Book;
import com.example.library_api.entities.Reader;
import com.example.library_api.entities.TransactionBook;
import com.example.library_api.exceptions.NotFoundException;
import com.example.library_api.repositories.BookRepository;
import com.example.library_api.repositories.ReaderRepository;
import com.example.library_api.repositories.TransactionBookRepository;
import com.example.library_api.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TransactionBookServiceImpl implements TransactionService {
    @Autowired
private TransactionBookRepository transactionBookRepository;
    @Autowired
    private ReaderRepository readerRepository;
    @Autowired
    private BookRepository bookRepository;


    @Override
    public TransactionBook performTransaction(Long readerId, Long bookId, String operationType) {
        //Находим читателя и книгу по ID.
        Reader reader = readerRepository.findById(readerId)
                .orElseThrow(() -> new NotFoundException("Reader not found"));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundException("Book is not found"));
        // Создание и настройка объекта TransactionBook
        TransactionBook transactionBook =new TransactionBook();
        transactionBook.setBook(book);
        transactionBook.setReader(reader);
        transactionBook.setTypeOperation(operationType);
        transactionBook.setDateTime(LocalDateTime.now());
        // Логирование перед сохранением
        System.out.print("Transction details Book=" + transactionBook.getBook().getName()+
                ", Reader details = "+transactionBook.getReader().getName()+ ", Transaction details "+
                transactionBook.getTypeOperation());
        switch (operationType.toLowerCase()) {
            case "issue":
                transactionBook.setTypeOperation("issue");
                // Логика для выдачи книги
                break;
            case "return":
                transactionBook.setTypeOperation("return");
                // Логика для возврата книги
                break;
            case "reserve":
                transactionBook.setTypeOperation("reserve");
                // Логика для резервирования книги
                break;
            default:
                throw new IllegalArgumentException("Invalid operation type");
        }

        return transactionBookRepository.save(transactionBook);
    }

    @Override
    public Author getMostPopularAuthor(LocalDate startDate, LocalDate endDate) {

        // Преобразуем LocalDate в LocalDateTime, задавая время на 00:00:00
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59);

        // Получаем все транзакции в указанном диапазоне дат
        List<TransactionBook> transactions = transactionBookRepository.findAllByDateTimeBetween(startDateTime,endDateTime);

        // Считаем количество транзакций по каждому автору
        Map<Author, Long> authorPopularity = transactions.stream()
                .flatMap(transactionBook -> transactionBook.getBook().getAuthors().stream())
                        .collect(Collectors.groupingBy(author -> author, Collectors.counting()));
        return authorPopularity.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }
}
