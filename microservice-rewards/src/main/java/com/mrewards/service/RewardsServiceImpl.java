package com.mrewards.service;

import com.mrewards.dto.AttractionDto;
import com.mrewards.dto.VisitedLocationDto;
import com.mrewards.model.User;
import com.mrewards.model.UserReward;
import com.mrewards.proxies.MicroserviceAttractionProxy;
import com.mrewards.proxies.MicroserviceUsersProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class RewardsServiceImpl implements RewardsService {


    @Autowired
    private RewardCentralService rewardCentralService;

    @Autowired
    MicroserviceAttractionProxy microserviceAttractionProxy;

    @Autowired
    MicroserviceUsersProxy microserviceUsersProxy;

    public RewardsServiceImpl(RewardCentralService rewardCentralService) {
        this.rewardCentralService = rewardCentralService;
    }

    @Async(value = "taskExecutor")
    @Override
    public CompletableFuture<Void> calculateRewards(User user) {
        List<VisitedLocationDto> userLocations = user.getVisitedLocations();
        List<AttractionDto> attractions = getAllAttractions();

        for (VisitedLocationDto visitedLocation : userLocations) {
            for (AttractionDto attraction : attractions) {
                rewardCentralService.sendReward(user, attraction, visitedLocation);
            }
        }
        return CompletableFuture.completedFuture(null);
    }





    @Override
    public List<UserReward> getUserRewards(User user) {
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
