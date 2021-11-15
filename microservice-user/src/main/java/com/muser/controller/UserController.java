package com.muser.controller;

import com.muser.model.User;
import com.muser.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Exposing the REST services of Rewards to other microservices.
 *
 * @see com.muser.service.UserService
 */
@RestController
public class UserController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Mapping a GET request in order to fetch the user with the given name.
     *
     * @param userName, method parameter which should be bound to the web request parameter.
     * @return the user with the given name.
     */
    @GetMapping(value = "/getUser")
    public User getUser(@RequestParam("userName") String userName) {
        logger.info("Successfully fetched the user with the following name : " + userName);
        return userService.getUser(userName);
    }

    /**
     * Mapping a GET request in order to fetch users.
     *
     * @return all users saved in TourGuide.
     */
    @GetMapping(value = "/getAllUsers")
    public CopyOnWriteArrayList<User> getAllUsers() {
        logger.info("Users have been successfully fetched.");
        return userService.getAllUsers();
    }
}
