package com.homework.payamigo.services;

import com.homework.payamigo.entities.User;
import com.homework.payamigo.entities.Wallet;
import com.homework.payamigo.exceptions.WalletNotFoundException;
import com.homework.payamigo.repositories.UserRepository;
import com.homework.payamigo.repositories.WalletRepository;
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
public class WalletService {
    @Autowired
    WalletRepository walletRepository;

    @Autowired
    UserRepository userRepository;

    public List<Wallet> getAllWallets(){
        return walletRepository.findAll();
    }

    public Optional<Wallet> getWalletById(Long walletId){
        return walletRepository.findById(walletId);
    }

    public Wallet insertWallet(Wallet wallet){
        return walletRepository.save(wallet);
    }

    public Wallet updateWallet(Long walletId, Wallet updatedWallet){
        Optional<Wallet> existingWallet = walletRepository.findById(walletId);
        if (existingWallet.isPresent()){
            Wallet wallet = existingWallet.get();
            wallet.setName(updatedWallet.getName());
            wallet.setUser(updatedWallet.getUser());
            wallet.setBalance(updatedWallet.getBalance());
            wallet.setCurrency(updatedWallet.getCurrency());
            return walletRepository.save(wallet);
        } else {
            throw new WalletNotFoundException("Wallet with ID: " + walletId + " not found!");
        }
    }

    public void deleteWallet(Long walletId){
        walletRepository.deleteById(walletId);
    }

    public boolean associateWalletWithUser(Long walletId, Long userId) {
        Wallet wallet = walletRepository.findById(walletId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);

        if (wallet == null || user == null) {
            return false;
        }

        wallet.setUser(user);
        walletRepository.save(wallet);

        return true;
    }
}
