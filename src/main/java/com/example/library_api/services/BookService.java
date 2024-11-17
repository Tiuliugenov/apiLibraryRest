package com.example.library_api.services;

import com.example.library_api.entities.Book;
import com.example.library_api.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public interface BookService {

List<Book> getAllBooks();
Book addBook (Book book);

}
