package com.mrewards.controller;

import com.mrewards.model.User;
import com.mrewards.model.UserReward;
import com.mrewards.service.RewardsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;


/**
 * Exposing the REST services of Rewards to other microservices.
 *
 * @see com.mrewards.proxies.MicroserviceUsersProxy
 * @see com.mrewards.service.RewardsService
 */
@RestController
public class RewardsController {
    private Logger logger = LoggerFactory.getLogger(RewardsController.class);
    private User user;

    @Autowired
    private RewardsService rewardsService;

    public RewardsController(RewardsService rewardsService) {
        this.rewardsService = rewardsService;
    }

    /**
     * Mapping a GET request in order to calculate the rewards for the user with the given name.
     *
     * @param userName, method parameter which should be bound to the web request parameter.
     * @return the new CompletableFuture.
     */
    @GetMapping(value = "/calculateRewards")
    @ResponseStatus(HttpStatus.OK)
    public CompletableFuture<Void> calculateRewards(@RequestParam String userName) {
        user = rewardsService.getUser(userName);
        logger.info("The rewards have been successfully calculated");
        return rewardsService.calculateRewards(user);
    }

    /**
     * Mapping a GET request in order to fetch rewards for the user with the given name.
     *
     * @param userName, method parameter which should be bound to the web request parameter.
     * @return all rewards of the user with the given name.
     */
    @GetMapping("/getRewards")
    public List<UserReward> getRewards(@RequestParam String userName) {
        user = rewardsService.getUser(userName);
        logger.info("The rewards have been successfully fetched.");
        return rewardsService.getUserRewards(user);
    }
}
