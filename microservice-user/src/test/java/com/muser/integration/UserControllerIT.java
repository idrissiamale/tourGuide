package com.muser.integration;

import com.muser.controller.UserController;
import com.muser.model.User;
import com.muser.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerIT {
    private UserController userController;
    @Autowired
    private UserService userService;

    @BeforeEach
    public void setUp() {
        userController = new UserController(userService);
    }

    @Test
    public void shouldReturnTheUserWhenFoundByUsername() {
        User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");

        userController.getUser(user.getUserName());

        assertEquals("jon", user.getUserName());
    }

    @Test
    public void shouldReturnAllUsers() {
        CopyOnWriteArrayList<User> users = userController.getAllUsers();

        assertEquals(1000,users.size());
    }
}
