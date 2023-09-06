package com.homework.payamigo;

import com.homework.payamigo.entities.Transaction;
import com.homework.payamigo.entities.User;
import com.homework.payamigo.entities.Wallet;
import com.homework.payamigo.exceptions.WalletNotFoundException;
import com.homework.payamigo.services.TransactionService;
import com.homework.payamigo.services.UserService;
import com.homework.payamigo.services.WalletService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TransactionServiceTests {

    @Autowired
    TransactionService transactionService;

    @Autowired
    UserService userService;

    @Autowired
    WalletService walletService;

    @Test
    public void testGetAllTransactions() {
        List<Transaction> transactions = transactionService.getAllTransactions();
        assertEquals(1, transactions.size());
    }

    @Test
    public void testInsertTransaction() {
        User user = new User(1001L,"Alice", "alice@example.com", "password1003");
        userService.insertUser(user);

        Wallet sourceWallet = new Wallet(10L,"Source Wallet", user, 100.0, "usd");
        Wallet destinationWallet = new Wallet(11L,"Destination Wallet", user, 200.0, "usd");
        sourceWallet.setUser(user);
        destinationWallet.setUser(user);
        walletService.insertWallet(sourceWallet);
        walletService.insertWallet(destinationWallet);

        Transaction transaction = new Transaction(1L, sourceWallet, destinationWallet, 25.0, 5.0, 10.0, "usd", "10-10-2020");

        Transaction insertedTransaction = transactionService.insertTransaction(transaction);

        assertNotNull(insertedTransaction.getId());
        assertEquals(transaction.getSourceWallet(), insertedTransaction.getSourceWallet());
        assertEquals(transaction.getDestinationWallet(), insertedTransaction.getDestinationWallet());
        assertEquals(transaction.getAmount(), insertedTransaction.getAmount());
        assertNotNull(insertedTransaction.getDatetime());
    }

    @Test
    public void testDestinationWalletNotExist() {
        User user = new User(1001L, "Bob", "bob@example.com", "passwordBob");
        userService.insertUser(user);
        Wallet sourceWallet = new Wallet(3L,"Source Wallet",user, 100.0, "usd");
        walletService.insertWallet(sourceWallet);

        Transaction transaction = new Transaction(2L, sourceWallet, new Wallet(), 25.0, 5.0, 10.0, "usd", "10-10-2020");

        assertThrows(WalletNotFoundException.class, () -> {
            transactionService.processTransaction(transaction);
        });
    }

    @Test
    public void testSourceWalletNotExist() {
        User user = new User(1001L, "Bob", "bob@example.com", "passwordBob");
        userService.insertUser(user);
        Wallet destinationWallet = new Wallet(3L,"Source Wallet",user, 100.0, "usd");
        walletService.insertWallet(destinationWallet);

        Transaction transaction = new Transaction(2L, new Wallet(), destinationWallet, 25.0, 5.0, 10.0, "usd", "10-10-2020");

        assertThrows(WalletNotFoundException.class, () -> {
            transactionService.processTransaction(transaction);
        });
    }
}
