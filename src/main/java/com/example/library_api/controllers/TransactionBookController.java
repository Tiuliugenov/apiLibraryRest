package com.example.library_api.controllers;
import com.example.library_api.entities.TransactionBook;
import com.example.library_api.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/booktransaction")

public class TransactionBookController {
    @Autowired
    TransactionService transactionService;

    @PostMapping("/perform")
    public ResponseEntity<TransactionBook> performTransaction(
            @RequestParam Long readerId,
            @RequestParam Long bookId,
            @RequestParam String operationType) {

        TransactionBook transactionBook = transactionService.performTransaction(readerId, bookId, operationType);
        return ResponseEntity.ok(transactionBook);

    }
}