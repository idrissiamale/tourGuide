package com.mrewards.service;

import com.mrewards.dto.AttractionDto;
import com.mrewards.model.User;
import com.mrewards.model.UserReward;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * RewardsService's role is to send reward points to the user if he has visited a tourist attraction.
 */
public interface RewardsService {
    /**
     * An asynchronous method which calculates the reward points to send to the user.
     * To get the reward points, He must have visited an attraction and not have received a reduction for this attraction.
     *
     * @param user, it refers to the registered user.
     * @return the new CompletableFuture.
     * @see com.mrewards.model.User
     */
    CompletableFuture<Void> calculateRewards(User user);

    /**
     * Fetching user's reward points.
     *
     * @param user, it refers to the registered user.
     * @return a thread-safe ArrayList of rewards.
     * @see com.mrewards.model.User
     */
    CopyOnWriteArrayList<UserReward> getUserRewards(User user);

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

    /**
     * Fetching tourist attractions from Attraction Microservice.
     *
     * @return a thread-safe ArrayList of attractions.
     */
    CopyOnWriteArrayList<AttractionDto> getAllAttractions();

}
