package com.mrewards.integration;

import com.mrewards.dto.AttractionDto;
import com.mrewards.dto.VisitedLocationDto;
import com.mrewards.model.User;
import com.mrewards.service.RewardsService;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RewardsServiceImplIT {
    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    private RewardsService rewardsService;

    @Test
    public void shouldGetUserRewardsFor100000UsersInLessThan20Minutes() throws InterruptedException, ExecutionException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        AttractionDto attraction = rewardsService.getAllAttractions().get(0);
        List<User> users = rewardsService.getAllUsers();
        for (User user : users) {
            user.clearVisitedLocations();
            user.addToVisitedLocations(new VisitedLocationDto(user.getUserId(), attraction, new Date()));
        }

        for (User user : users) {
            rewardsService.calculateRewards(user);
        }

        for (User user : users) {
            while (user.getUserRewards().isEmpty()) {
                try {
                    this.taskExecutor.getThreadPoolExecutor().awaitTermination(200, TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {
                }
            }
        }

        stopWatch.stop();
        for (User user : users) {
            assertEquals(1, rewardsService.getUserRewards(user).size());
        }
        System.out.println("highVolumeGetRewards: Time Elapsed: " + TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()) + " seconds.");
        assertTrue(TimeUnit.MINUTES.toSeconds(20) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
    }
}
