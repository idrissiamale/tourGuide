package com.mrewards.service;

import com.mrewards.dto.AttractionDto;
import com.mrewards.dto.LocationDto;
import com.mrewards.dto.VisitedLocationDto;
import com.mrewards.model.User;
import com.mrewards.model.UserReward;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import rewardCentral.RewardCentral;

@Service
public class RewardCentralService {
    private static final double STATUTE_MILES_PER_NAUTICAL_MILE = 1.15077945;

    // proximity in miles
    private int defaultProximityBuffer = 10;
    private int proximityBuffer = defaultProximityBuffer;

    @Autowired
    private RewardCentral rewardsCentral;

    public RewardCentralService(RewardCentral rewardsCentral) {
        this.rewardsCentral = rewardsCentral;
    }

    public void setProximityBuffer(int proximityBuffer) {
        this.proximityBuffer = proximityBuffer;
    }

    public void setDefaultProximityBuffer() {
        proximityBuffer = defaultProximityBuffer;
    }

    @Async(value = "taskExecutorRewardsPoint")
    public void sendReward(User user, AttractionDto attraction, VisitedLocationDto visitedLocation) {
        if (hasNoReward(user, attraction) && nearAttraction(visitedLocation, attraction)) {
            user.addUserReward(new UserReward(visitedLocation, attraction, getRewardPoints(attraction, user)));
        }
    }

    private boolean hasNoReward(User user, AttractionDto attraction) {
        return user.getUserRewards().stream()
                .noneMatch(reward -> reward.getAttraction().getAttractionName().equals(attraction.getAttractionName()));
    }

    private int getRewardPoints(AttractionDto attraction, User user) {
        return rewardsCentral.getAttractionRewardPoints(attraction.getAttractionId(), user.getUserId());
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
}
