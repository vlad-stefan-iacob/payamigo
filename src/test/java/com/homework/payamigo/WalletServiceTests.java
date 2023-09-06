package com.homework.payamigo;

import com.homework.payamigo.entities.User;
import com.homework.payamigo.entities.Wallet;
import com.homework.payamigo.repositories.WalletRepository;
import com.homework.payamigo.services.UserService;
import com.homework.payamigo.services.WalletService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class WalletServiceTests {

    @Autowired
    WalletService walletService;

    @Autowired
    UserService userService;

    @Autowired
    WalletRepository walletRepository;

    @Test
    public void testWalletNotAssociatedWithUser() {
        User user = new User(1000L,"John John", "john@example.com", "password");
        userService.insertUser(user);

        Wallet wallet = new Wallet(6L, "John's Wallet", user, 4500.0, "ron");
        walletService.insertWallet(wallet);

        boolean isAssociated = walletService.associateWalletWithUser(wallet.getId(), user.getId());

        assertTrue(isAssociated);
    }

    @Test
    public void testGetAllWallets() {
        List<Wallet> wallets = walletService.getAllWallets();
        assertEquals(4, wallets.size());
    }

    @Test
    public void testInsertWallet(){
        User user = new User(1001L,"Ana", "ana@example.com", "anapass");
        userService.insertUser(user);

        Wallet wallet = new Wallet(7L, "Wallet for delete", user, 500.0, "usd");
        walletService.insertWallet(wallet);
        assertNotNull(wallet);
    }

    @Test
    public void testDeleteWallet() {
        User user = new User(1001L,"Ana", "ana@example.com", "anapass");
        userService.insertUser(user);

        Wallet wallet = new Wallet(7L, "Wallet for delete", user, 500.0, "usd");
        walletService.insertWallet(wallet);

        walletService.deleteWallet(wallet.getId());

        assertNull(walletRepository.findById(wallet.getId()).orElse(null));
    }

    @Test
    public void testWalletNegativeBalance(){
        User user = new User(1001L,"Ana", "ana@example.com", "anapass");
        userService.insertUser(user);

        Wallet wallet2 = new Wallet(8L, "Wallet with neg balance", user, -20.0, "ron");

        if (wallet2.getBalance() < 0){
            throw new IllegalArgumentException("Negative balance");
        }
    }
}
