package com.mrewards.service;

import com.mrewards.dto.AttractionDto;
import com.mrewards.model.User;
import com.mrewards.model.UserReward;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;

public interface RewardsService {
    CompletableFuture<Void> calculateRewards(User user) throws ExecutionException, InterruptedException;

    CopyOnWriteArrayList<UserReward> getUserRewards(User user) throws ExecutionException, InterruptedException;

    User getUser(String userName);

    CopyOnWriteArrayList<User> getAllUsers();

    CopyOnWriteArrayList<AttractionDto> getAllAttractions();

}
