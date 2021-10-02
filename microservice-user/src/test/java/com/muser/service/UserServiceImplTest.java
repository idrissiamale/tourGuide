package com.muser.service;

import com.muser.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserServiceImplTest {
    private UserServiceImpl userServiceImpl;
    private User user;
    private User user2;


    @BeforeEach
    public void setUp() {
        userServiceImpl = new UserServiceImpl();
        user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
        user2 = new User(UUID.randomUUID(), "jon2", "000", "jon2@tourGuide.com");
    }

    @Test
    public void addUser() {
        userServiceImpl.addUser(user);

        assertNotNull(user);
    }

    @Test
    public void addUser2() {
        userServiceImpl.addUser(user);

        assertEquals("jon", user.getUserName());
    }

    @Test
    public void getUser() {
        userServiceImpl.getUser(user2.getUserName());

        assertEquals("jon2", user2.getUserName());
    }

    @Test
    public void getAllUsers() {
        userServiceImpl.addUser(user);

        List<User> users = userServiceImpl.getAllUsers();

        System.out.println(users.get(0).getUserName());

        assertTrue(users.contains(user));
    }

}
