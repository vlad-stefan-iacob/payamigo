package com.homework.payamigo.services;

import com.homework.payamigo.entities.User;
import com.homework.payamigo.exceptions.UserNotFoundException;
import com.homework.payamigo.repositories.UserRepository;
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
public class UserService {
    @Autowired
    UserRepository userRepository;

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long userId){
        return userRepository.findById(userId);
    }

    public User insertUser(User user){
        return userRepository.save(user);
    }

    public User updateUser(Long userId, User updatedUser){
        Optional<User> existingUser = userRepository.findById(userId);
        if (existingUser.isPresent()){
            User user = existingUser.get();
            user.setName(updatedUser.getName());
            user.setEmail(updatedUser.getEmail());
            user.setPassword(updatedUser.getPassword());
            return userRepository.save(user);
        } else {
            throw new UserNotFoundException("User with ID: " + userId + " not found!");
        }
    }
    public void deleteUser(Long userId){
        userRepository.deleteById(userId);
    }

    public String[] getUserNames() {
        List<User> users = userRepository.findAll();
        int maxUserCount = Math.min(5, users.size());
        String[] userNames = new String[maxUserCount];

        for (int i = 0; i < maxUserCount; i++) {
            userNames[i] = users.get(i).getName();
        }

        return userNames;
    }
}
