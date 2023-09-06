package com.homework.payamigo;

import com.homework.payamigo.entities.User;
import com.homework.payamigo.repositories.UserRepository;
import com.homework.payamigo.services.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Duration;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertTimeout;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTests {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testGetAllUsers() {
        List<User> users = userService.getAllUsers();
        assertEquals(1000, users.size());
    }

    @Test
    public void testInsertUser(){
        User user = new User(3L, "gigel", "gigel@gmail.com", "parola");
        User savedUser = userService.insertUser(user);
        assertNotNull(savedUser);
    }

    @Test
    public void testUpdateUser() {
        User user = new User(4L, "John", "john@example.com", "password");
        User savedUser = userRepository.save(user);

        User updatedUser = new User(4L, "Updated John", "updated@example.com", "newpassword");
        User result = userService.updateUser(savedUser.getId(), updatedUser);

        assertEquals("Updated John", result.getName());
        assertEquals("updated@example.com", result.getEmail());
    }

    @Test
    public void testGetAllUsersExecutionTime() {
        //add a lot o users in database
//        for (Long i = 5L; i < 1000L; i++) {
//            User user = new User(i, "User" + i, "user" + i + "@example.com", "password" + i);
//            userService.insertUser(user);
//        }

        //test if all users are shown in time
        assertTimeout(Duration.ofMillis(500), () -> {
            List<User> users = userService.getAllUsers();
        });
    }

    @Test
    public void testGetUserNames() {
        // it is tested just with 5 usernames, because I have a lot of data in DB (needed for previous test)
        String[] actualNames = userService.getUserNames();
        String[] expectedNames = {"John Doe", "ionel", "gigel", "Updated John", "User5"};
        assertArrayEquals(expectedNames, actualNames);
    }



}
