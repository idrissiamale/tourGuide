package com.mtracker.service;

import com.mtracker.model.User;
import com.mtracker.proxies.MicroserviceRewardsProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class RewardsCentralService {
    @Autowired
    MicroserviceRewardsProxy microserviceRewardsProxy;

    //@Async(value = "taskExecutorReward")
    public CompletableFuture<Void> calculateRewards(User user) {
        microserviceRewardsProxy.calculateRewards(user.getUserName());
        return CompletableFuture.completedFuture(null);
    }
}
