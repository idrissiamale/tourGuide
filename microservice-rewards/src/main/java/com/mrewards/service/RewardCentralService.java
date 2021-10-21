package com.mrewards.service;

import com.mrewards.dto.AttractionDto;
import com.mrewards.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import rewardCentral.RewardCentral;

import java.util.concurrent.CompletableFuture;

@Service
public class RewardCentralService {
    private final RewardCentral rewardCentral;

    @Autowired
    public RewardCentralService(RewardCentral rewardCentral) {
        this.rewardCentral = rewardCentral;
    }

    @Async(value = "taskExecutorRewardsPoint")
    public CompletableFuture<Integer> getRewardPoints(AttractionDto attraction, User user) {
       int rewardPoints = rewardCentral.getAttractionRewardPoints(attraction.getAttractionId(), user.getUserId());
       return CompletableFuture.completedFuture(rewardPoints);
    }
}
