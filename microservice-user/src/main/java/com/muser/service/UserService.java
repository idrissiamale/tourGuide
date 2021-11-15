package com.muser.service;


import com.muser.model.User;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * UserService's role is to manage TourGuide's users.
 */
public interface UserService {
    /**
     * Retrieves a user by its username.
     *
     * @param userName, the given name.
     * @return the user with the given name.
     */
    User getUser(String userName);

    /**
     * Fetching all users.
     *
     * @return a thread-safe ArrayList of users.
     */
    CopyOnWriteArrayList<User> getAllUsers();

    /**
     * Adding a user.
     * @param user, it refers to the user we want to add.
     */
    void addUser(User user);
}
