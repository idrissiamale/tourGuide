package com.mrewards;

import com.mrewards.dto.AttractionDto;
import com.mrewards.dto.VisitedLocationDto;
import com.mrewards.model.User;
import com.mrewards.proxies.MicroserviceAttractionProxy;
import com.mrewards.proxies.MicroserviceUsersProxy;
import com.mrewards.service.RewardCentralService;
import com.mrewards.service.RewardsServiceImpl;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.jupiter.api.BeforeEach;
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
public class RewardsTest {
    private RewardsServiceImpl rewardsService;

    @Autowired
    private ThreadPoolTaskExecutor taskExecutorRewardsPoint;

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    private RewardCentralService rewardCentralService;

    @Autowired
    MicroserviceAttractionProxy microserviceAttractionProxy;

    @Autowired
    MicroserviceUsersProxy microserviceUsersProxy;

    @BeforeEach
    public void setUp() {
        rewardsService = new RewardsServiceImpl(rewardCentralService, microserviceAttractionProxy, microserviceUsersProxy);
    }

    @Test
    public void testClass() throws InterruptedException, ExecutionException {
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

        for(User user : users) {
            while(user.getUserRewards().isEmpty()) {
                try {
                    this.taskExecutor.getThreadPoolExecutor().awaitTermination(200, TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {
                }
            }
        }

        stopWatch.stop();
        for(User user : users) {
            //System.out.println(user.getUserRewards().size());
            assertEquals(1, rewardsService.getUserRewards(user).size());
        }
        System.out.println("highVolumeGetRewards: Time Elapsed: " + TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()) + " seconds.");
        assertTrue(TimeUnit.MINUTES.toSeconds(20) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
    }
}
