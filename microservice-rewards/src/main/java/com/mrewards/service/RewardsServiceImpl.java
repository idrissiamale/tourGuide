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

@Service
public class RewardsServiceImpl implements RewardsService {
    private static final double STATUTE_MILES_PER_NAUTICAL_MILE = 1.15077945;

    // proximity in miles
    private int defaultProximityBuffer = 10;
    private int proximityBuffer = defaultProximityBuffer;
    private final RewardCentralService rewardCentralService;
    private final MicroserviceAttractionProxy microserviceAttractionProxy;
    private final MicroserviceUsersProxy microserviceUsersProxy;

    @Autowired
    public RewardsServiceImpl(RewardCentralService rewardCentralService, MicroserviceAttractionProxy microserviceAttractionProxy, MicroserviceUsersProxy microserviceUsersProxy) {
        this.rewardCentralService = rewardCentralService;
        this.microserviceAttractionProxy = microserviceAttractionProxy;
        this.microserviceUsersProxy = microserviceUsersProxy;
    }

    @Override
    public CompletableFuture<Void> calculateRewards(User user) throws ExecutionException, InterruptedException {
        List<VisitedLocationDto> userLocations = user.getVisitedLocations();
        List<AttractionDto> attractions = getAttractions();
        for (VisitedLocationDto visitedLocation : userLocations) {
            for (AttractionDto attraction : attractions) {
                if (hasNoReward(user, attraction) && nearAttraction(visitedLocation, attraction)) {
                    rewardCentralService.getRewardPoints(attraction, user);
                    user.addUserReward(new UserReward(visitedLocation, attraction));
                    //System.out.println(user.getUserRewards().size());
                }
            }
        }
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public List<AttractionDto> getAttractions() {
        return microserviceAttractionProxy.getAllAttractions();
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

    private boolean hasNoReward(User user, AttractionDto attraction) {
        return user.getUserRewards().stream().noneMatch(reward -> reward.getAttraction().getAttractionName().equals(attraction.getAttractionName()));
    }

    private boolean nearAttraction(VisitedLocationDto visitedLocation, AttractionDto attraction) {
        return getDistance(attraction, visitedLocation.getLocationDto()) <= proximityBuffer;
    }

    private double getDistance(LocationDto loc1, LocationDto loc2) {
        double lat1 = Math.toRadians(loc1.getLatitude());
        double lon1 = Math.toRadians(loc1.getLongitude());
        double lat2 = Math.toRadians(loc2.getLatitude());
        double lon2 = Math.toRadians(loc2.getLongitude());

        double angle = Math.acos(Math.sin(lat1) * Math.sin(lat2)
                + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));

        double nauticalMiles = 60 * Math.toDegrees(angle);
        return STATUTE_MILES_PER_NAUTICAL_MILE * nauticalMiles;
    }


    //@Async("taskExecutor")
    //public int getRewardPoints(AttractionDto attraction, User user) {
    //return rewardsCentral.getAttractionRewardPoints(attraction.getAttractionId(), user.getUserId());
    //}

}
