package com.example.library_api.controllers;

import com.example.library_api.entities.Reader;
import com.example.library_api.services.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/popularreader")
public class ReaderController {
    @Autowired
    private ReaderService readerService;
    @GetMapping
    public Reader getPopularReader (){

        return readerService.getMostPopularReader();
    }
    @GetMapping(value = "/sortedByBooks")
    public List<Reader> getAllReadeSortedByBooks() {
        return readerService.getReadersSortedByUnreturnedBooks();
    }
}
