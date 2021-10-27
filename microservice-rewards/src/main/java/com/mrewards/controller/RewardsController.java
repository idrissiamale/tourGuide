package com.mrewards.controller;

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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
public class RewardsController {
    private User user;

    @Autowired
    private RewardsService rewardsService;

    public RewardsController(RewardsService rewardsService) {
        this.rewardsService = rewardsService;
    }

    @GetMapping(value = "/calculateRewards")
    @ResponseStatus(HttpStatus.OK)
    public CompletableFuture<Void> calculateRewards(@RequestParam String userName) throws ExecutionException, InterruptedException {
        user = rewardsService.getUser(userName);
        return rewardsService.calculateRewards(user);
    }

    @GetMapping("/getRewards")
    public List<UserReward> getRewards(@RequestParam String userName) throws ExecutionException, InterruptedException {
        user = rewardsService.getUser(userName);
        return rewardsService.getUserRewards(user);
    }
}
