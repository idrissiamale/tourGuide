package com.mrewards.service;

import com.mrewards.dto.AttractionDto;
import com.mrewards.dto.LocationDto;
import com.mrewards.dto.VisitedLocationDto;
import com.mrewards.model.User;
import com.mrewards.model.UserReward;
import com.mrewards.proxies.MicroserviceAttractionProxy;
import com.mrewards.proxies.MicroserviceUsersProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import rewardCentral.RewardCentral;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;

@Service
public class RewardCentralService {
    private static final double STATUTE_MILES_PER_NAUTICAL_MILE = 1.15077945;

    // proximity in miles
    private int defaultProximityBuffer = 10;
    private int proximityBuffer = defaultProximityBuffer;
    @Autowired
    private RewardCentral rewardCentral;

    @Autowired
    MicroserviceAttractionProxy microserviceAttractionProxy;

    @Autowired
    MicroserviceUsersProxy microserviceUsersProxy;

    public void setProximityBuffer(int proximityBuffer) {
        this.proximityBuffer = proximityBuffer;
    }

    public void setDefaultProximityBuffer() {
        proximityBuffer = defaultProximityBuffer;
    }


    public RewardCentralService(RewardCentral rewardCentral, MicroserviceAttractionProxy microserviceAttractionProxy, MicroserviceUsersProxy microserviceUsersProxy) {
        this.rewardCentral = rewardCentral;
        this.microserviceAttractionProxy = microserviceAttractionProxy;
        this.microserviceUsersProxy = microserviceUsersProxy;
    }

    @Async(value = "taskExecutor")
    public void sendRewardPoints(User user) {
        List<VisitedLocationDto> userLocations = user.getVisitedLocations();
        List<AttractionDto> attractions = getAllAttractions();
        for (VisitedLocationDto visitedLocation : userLocations) {
            for (AttractionDto attraction : attractions) {
                if (hasNoReward(user, attraction) && nearAttraction(visitedLocation, attraction)) {
                    addRewardPoints(user, attraction, visitedLocation);
                }
            }
        }
    }

    private void addRewardPoints(User user, AttractionDto attraction, VisitedLocationDto visitedLocation) {
        int rewardPoints = rewardCentral.getAttractionRewardPoints(user.getUserId(), attraction.getAttractionId());
        user.addUserReward(new UserReward(visitedLocation, attraction, rewardPoints));
    }


    private boolean hasNoReward(User user, AttractionDto attraction) {
        return user.getUserRewards().stream()
                .noneMatch(reward -> reward.getAttraction().getAttractionName().equals(attraction.getAttractionName()));
    }

    private boolean nearAttraction(VisitedLocationDto visitedLocation, AttractionDto attraction) {
        return !(getDistance(attraction, visitedLocation.getLocationDto()) > proximityBuffer);
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

    public List<UserReward> getUserRewards(User user) throws ExecutionException, InterruptedException {
        return user.getUserRewards();
    }

    public User getUser(String userName) {
        return microserviceUsersProxy.getUser(userName);
    }


    public CopyOnWriteArrayList<User> getAllUsers() {
        return microserviceUsersProxy.getAllUsers();
    }


    public List<AttractionDto> getAllAttractions() {
        return microserviceAttractionProxy.getAllAttractions();
    }
}
