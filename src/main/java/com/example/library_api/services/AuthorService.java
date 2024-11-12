package com.example.library_api.services;

import com.example.library_api.entities.Author;

import java.util.List;

public interface AuthorService {
    List <Author> getAllAuthors();
    Author addAuthor(Author author);
}
