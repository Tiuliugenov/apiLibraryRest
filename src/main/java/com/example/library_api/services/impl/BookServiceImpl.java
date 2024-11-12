package com.example.library_api.services.impl;

import com.example.library_api.entities.Book;
import com.example.library_api.repositories.BookRepository;
import com.example.library_api.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;
    @Override
    public List<Book> getAllBooks() {

        return bookRepository.findAll();
    }

    @Override
    public Book addBook(Book book) {

        return bookRepository.save(book);
    }
}
