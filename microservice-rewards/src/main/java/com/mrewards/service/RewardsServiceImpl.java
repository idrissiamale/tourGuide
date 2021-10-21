package com.mrewards.service;

import com.mrewards.dto.AttractionDto;
import com.mrewards.dto.LocationDto;
import com.mrewards.dto.VisitedLocationDto;
import com.mrewards.model.User;
import com.mrewards.model.UserReward;
import com.mrewards.proxies.MicroserviceAttractionProxy;
import com.mrewards.proxies.MicroserviceUsersProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class RewardsServiceImpl implements RewardsService {
    private static final double STATUTE_MILES_PER_NAUTICAL_MILE = 1.15077945;

    // proximity in miles
    private int defaultProximityBuffer = 10;
    private int proximityBuffer = defaultProximityBuffer;
    @Autowired
    private RewardCentralService rewardCentralService;

    @Autowired
    MicroserviceAttractionProxy microserviceAttractionProxy;

    @Autowired
    MicroserviceUsersProxy microserviceUsersProxy;

    private ExecutorService executor = Executors.newFixedThreadPool(1000);

    public void setProximityBuffer(int proximityBuffer) {
        this.proximityBuffer = proximityBuffer;
    }

    public void setDefaultProximityBuffer() {
        proximityBuffer = defaultProximityBuffer;
    }


    public RewardsServiceImpl(RewardCentralService rewardCentralService, MicroserviceAttractionProxy microserviceAttractionProxy, MicroserviceUsersProxy microserviceUsersProxy) {
        this.rewardCentralService = rewardCentralService;
        this.microserviceAttractionProxy = microserviceAttractionProxy;
        this.microserviceUsersProxy = microserviceUsersProxy;
    }

    @Override
    public void calculateRewards(User user) throws ExecutionException, InterruptedException {
        rewardCentralService.sendRewardPoints(user);
    }


    @Override
    public List<UserReward> getUserRewards(User user) throws ExecutionException, InterruptedException {
        return user.getUserRewards();
    }

    @Override
    public User getUser(String userName) {
        return microserviceUsersProxy.getUser(userName);
    }

    @Override
    public List<User> getAllUsers() {
        return microserviceUsersProxy.getAllUsers();
    }

    @Override
    public List<AttractionDto> getAllAttractions() {
        return microserviceAttractionProxy.getAllAttractions();
    }


    //@Async("taskExecutor")
    //public int getRewardPoints(AttractionDto attraction, User user) {
    //return rewardsCentral.getAttractionRewardPoints(attraction.getAttractionId(), user.getUserId());
    //}

}

