package com.muser.service;

import com.muser.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.junit.jupiter.api.Assertions.*;

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
    public void shouldReturnNewUserWhenAdded() {
        userServiceImpl.addUser(user);

        assertNotNull(user);
    }

    @Test
    public void shouldGetTheSameUserNameWhenNewUserIsAdded() {
        userServiceImpl.addUser(user);

        assertEquals("jon", user.getUserName());
    }

    @Test
    public void shouldGetTheUser() {
        userServiceImpl.getUser(user2.getUserName());

        assertEquals("jon2", user2.getUserName());
    }

    @Test
    public void shouldContainTheNewUser() {
        userServiceImpl.addUser(user);

        CopyOnWriteArrayList<User> users = userServiceImpl.getAllUsers();

        assertTrue(users.contains(user));
    }
}
