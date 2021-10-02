package com.mrewards.service;

import com.mrewards.dto.AttractionDto;
import com.mrewards.model.User;
import com.mrewards.model.UserReward;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public interface RewardsService {
    CompletableFuture<Void> calculateRewards(User user) throws ExecutionException, InterruptedException;

    List<UserReward> getUserRewards(User user);

    User getUser(String userName);

    List<User> getAllUsers();

    List<AttractionDto> getAllAttractions();

}
