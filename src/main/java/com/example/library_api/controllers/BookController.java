package com.example.library_api.controllers;

import com.example.library_api.entities.Book;
import com.example.library_api.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/book")
public class BookController {
    @Autowired
    private BookService bookService;
    @GetMapping()
    public List<Book> getAllBooks() {
    return bookService.getAllBooks();}


    @PostMapping
            public Book addBook (@RequestBody Book book){

        return bookService.addBook(book);
        }
    }

