package com.muser.service;


import com.muser.model.User;

import java.util.concurrent.CopyOnWriteArrayList;

public interface UserService {
    User getUser(String userName);

    CopyOnWriteArrayList<User> getAllUsers();

    void addUser(User user);
}
