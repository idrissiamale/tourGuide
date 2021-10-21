package com.mrewards.Rewards;

import com.mrewards.model.User;
import com.mrewards.service.RewardsService;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class Rewards {
    private Logger logger = LoggerFactory.getLogger(Rewards.class);

    @Autowired
    private RewardsService rewardsService;

    public Rewards(RewardsService rewardsService) {
        this.rewardsService = rewardsService;
    }


    //@Async(value = "taskExecutor")
    public void getAllUsersRewards() throws ExecutionException, InterruptedException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        //AttractionDto attraction = rewardsService.getAllAttractions().get(0);
        //List<User> userList = rewardsService.getAllUsers();
        //userList.forEach(u -> u.addToVisitedLocations(new VisitedLocationDto(u.getUserId(), attraction, new Date())));
        //logger.info("Start Tracking " + userList.size() + "users");
        //userList.forEach(u -> {
            //rewardsService.calculateRewards(u);
        //});
        //userList.forEach(this::hasRewards);
        stopWatch.stop();
        logger.info("Complete Task 1, time-consuming:" + TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()) + " seconds.");
    }

    private boolean hasRewards(User user) {
        return user.getUserRewards().size() > 0;
    }
}
