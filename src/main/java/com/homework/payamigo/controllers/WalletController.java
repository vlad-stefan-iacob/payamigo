package com.homework.payamigo.controllers;

import com.homework.payamigo.entities.Wallet;
import com.homework.payamigo.services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class WalletController {
    @Autowired
    WalletService walletService;

    @GetMapping("/wallets")
    public List<Wallet> getAllWallets() {
        return walletService.getAllWallets();
    }

    @GetMapping("/wallet/{id}")
    public Optional<Wallet> getWalletById(@PathVariable Long walletId) {
        return walletService.getWalletById(walletId);
    }

    @PostMapping("/add-wallet")
    public Wallet createWallet(@RequestBody Wallet wallet) {
        return walletService.insertWallet(wallet);
    }

    @PutMapping("/update-wallet/{walletId}")
    public Wallet updateWallet(@PathVariable Long walletId, @RequestBody Wallet updatedWallet) {
        return walletService.updateWallet(walletId, updatedWallet);
    }

    @DeleteMapping("/delete-wallet/{walletId}")
    public String deleteWallet(@PathVariable Long walletId) {
        walletService.deleteWallet(walletId);
        return "Wallet deleted";
    }
}
