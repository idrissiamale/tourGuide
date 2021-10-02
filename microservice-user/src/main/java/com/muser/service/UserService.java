package com.muser.service;


import com.muser.model.User;

import java.util.List;

public interface UserService {
    User getUser(String userName);

    List<User> getAllUsers();

    void addUser(User user);
}
