package com.mrewards.model;

import com.mrewards.dto.VisitedLocationDto;
import lombok.Getter;
import lombok.ToString;

import java.util.*;

@Getter
@ToString
public class User {
    private final UUID userId;
    private final String userName;
    private String phoneNumber;
    private String emailAddress;
    private Date latestLocationTimestamp;
    private List<VisitedLocationDto> visitedLocations = new ArrayList<>();
    private List<UserReward> userRewards = new ArrayList<>();

    public User(UUID userId, String userName, String phoneNumber, String emailAddress) {
        this.userId = userId;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
    }

    public List<VisitedLocationDto> getVisitedLocations() {
        return Collections.unmodifiableList(visitedLocations);
    }

    public void addToVisitedLocations(VisitedLocationDto visitedLocation) {
        synchronized (this) {
            visitedLocations.add(visitedLocation);
        }
    }

    public void clearVisitedLocations() {
        visitedLocations.clear();
    }

    public VisitedLocationDto getLastVisitedLocation() {
        return visitedLocations.get(visitedLocations.size() - 1);
    }

    public Date getLatestLocationTimestamp() {
        return getLastVisitedLocation().getTimeVisited();
    }


    public List<UserReward> getUserRewards() {
        return Collections.unmodifiableList(userRewards);
    }

    public void addUserReward(UserReward userReward) {
        synchronized (this) {
            userRewards.add(userReward);
        }
    }
}
