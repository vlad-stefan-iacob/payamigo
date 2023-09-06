package com.homework.payamigo.controllers;

import com.homework.payamigo.entities.Transaction;
import com.homework.payamigo.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class TransactionController {
    @Autowired
    TransactionService transactionService;

    @GetMapping("/transactions")
    public List<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    @GetMapping("transaction/{transactionId}")
    public Optional<Transaction> getTransactionById(@PathVariable Long transactionId) {
        return transactionService.getTransactionById(transactionId);
    }

    @PostMapping("/add-transaction")
    public Transaction createTransaction(@RequestBody Transaction transaction) {
        return transactionService.insertTransaction(transaction);
    }

    @PutMapping("/update-transaction/{transactionId}")
    public Transaction updateTransaction(@PathVariable Long transactionId, @RequestBody Transaction updatedTransaction) {
        return transactionService.updateTransaction(transactionId, updatedTransaction);
    }

    @DeleteMapping("/delete-transaction/{transactionId}")
    public void deleteTransaction(@PathVariable Long transactionId) {
        transactionService.deleteTransaction(transactionId);
    }
}
