package com.mrewards.service;

import com.mrewards.dto.AttractionDto;
import com.mrewards.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import rewardCentral.RewardCentral;

import java.util.concurrent.CompletableFuture;

@Service
public class RewardsPointService {
    @Autowired
    private RewardCentral rewardsCentral;

    public RewardsPointService(RewardCentral rewardsCentral) {
        this.rewardsCentral = rewardsCentral;
    }

    @Async(value = "taskExecutorRewardsPoint")
    public CompletableFuture<Integer> getRewardPoints(AttractionDto attraction, User user) {
        int points = rewardsCentral.getAttractionRewardPoints(attraction.getAttractionId(), user.getUserId());
        return CompletableFuture.completedFuture(points);
    }

}
