package com.mtracker.service;

import com.mtracker.dto.VisitedLocationDto;
import com.mtracker.model.User;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;

/**
 * TrackerService's role is to track user location.
 */
public interface TrackerService {
    /**
     * An asynchronous method which tracks the user location.
     * If the location is an attraction then reward points are send to the user
     *
     * @param user, it refers to the registered user.
     * @return the new CompletableFuture.
     * @see com.mtracker.model.User
     */
    CompletableFuture<VisitedLocationDto> trackUserLocation(User user);

    /**
     * Retrieves a user by its username. This user is fetched from User Microservice.
     *
     * @param userName, the given name.
     * @return the user with the given name.
     */
    User getUser(String userName);

    /**
     * Fetching users from User Microservice.
     *
     * @return a thread-safe ArrayList of users.
     */
    CopyOnWriteArrayList<User> getAllUsers();
}
