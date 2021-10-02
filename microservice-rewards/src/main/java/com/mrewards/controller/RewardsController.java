package com.mrewards.controller;

import com.mrewards.Rewards.Rewards;
import com.mrewards.model.User;
import com.mrewards.model.UserReward;
import com.mrewards.service.RewardsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
public class RewardsController {
    private User user;

    @Autowired
    private RewardsService rewardsService;

    @Autowired
    private Rewards rewards;

    public RewardsController(RewardsService rewardsService, Rewards rewards) {
        this.rewardsService = rewardsService;
        this.rewards = rewards;
    }

    @GetMapping(value = "/calculateRewards")
    @ResponseStatus(HttpStatus.OK)
    public void calculateRewards(@RequestParam String userName) throws ExecutionException, InterruptedException {
        user = rewardsService.getUser(userName);
        rewardsService.calculateRewards(user);
    }

    @GetMapping("/getRewards")
    public List<UserReward> getRewards(@RequestParam String userName) {
        user = rewardsService.getUser(userName);
        return rewardsService.getUserRewards(user);
    }

    @GetMapping(value = "/getAllRewards")
    public void trackUsersLocation() throws ExecutionException, InterruptedException {
        rewards.getAllUsersRewards();
    }
}
