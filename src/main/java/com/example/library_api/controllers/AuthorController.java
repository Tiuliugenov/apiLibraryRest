package com.example.library_api.controllers;

import com.example.library_api.entities.Author;
import com.example.library_api.services.AuthorService;
import com.example.library_api.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping(value="/mostpopularauthors")
public class AuthorController {
    @Autowired
    private AuthorService authorService;
    @Autowired
    private TransactionService transactionService;
    @GetMapping
    public Author getPopularAuthor (@RequestParam String startDate, @RequestParam String endDate){
        System.out.println("startDate: " + startDate );  // Логирование параметров
        System.out.println("endDate: " + endDate );      // Логирование

        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);

        return transactionService.getMostPopularAuthor(start, end);
    }
    @PostMapping
    public Author addBook (@RequestBody Author author){
        return authorService.addAuthor(author);
    }
}
