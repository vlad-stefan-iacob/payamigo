package com.homework.payamigo.services;

import com.homework.payamigo.entities.Transaction;
import com.homework.payamigo.entities.Wallet;
import com.homework.payamigo.exceptions.TransactionNotFoundException;
import com.homework.payamigo.exceptions.WalletNotFoundException;
import com.homework.payamigo.repositories.TransactionRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionService {
    @Autowired
    TransactionRepository transactionRepository;

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Optional<Transaction> getTransactionById(Long transactionId) {
        return transactionRepository.findById(transactionId);
    }

    public Transaction insertTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public Transaction updateTransaction(Long transactionId, Transaction updatedTransaction) {
        Optional<Transaction> existingTransaction = transactionRepository.findById(transactionId);
        if (existingTransaction.isPresent()) {
            Transaction transaction = existingTransaction.get();
            transaction.setSourceWallet(updatedTransaction.getSourceWallet());
            transaction.setDestinationWallet(updatedTransaction.getDestinationWallet());
            transaction.setAmount(updatedTransaction.getAmount());
            return transactionRepository.save(transaction);
        } else {
            throw new TransactionNotFoundException("Transaction with ID: " + transactionId + " not found!");
        }
    }

    public void deleteTransaction(Long transactionId) {
        transactionRepository.deleteById(transactionId);
    }

    public void processTransaction(Transaction transaction) {
        Wallet sourceWallet = transaction.getSourceWallet();
        Wallet destinationWallet = transaction.getDestinationWallet();

        // Ensure the source and destination wallets exist
        if (sourceWallet == null || destinationWallet == null) {
            throw new WalletNotFoundException("Source or destination wallet not found");
        }
    }
}
