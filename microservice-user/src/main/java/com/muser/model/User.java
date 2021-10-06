package com.muser.model;

import com.muser.dto.VisitedLocationDto;
import lombok.Getter;
import lombok.ToString;
import tripPricer.Provider;

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
    private UserPreferences userPreferences = new UserPreferences();
    private List<Provider> tripDeals = new ArrayList<>();

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

    public List<UserReward> getUserRewards() {
        return Collections.unmodifiableList(userRewards);
    }

    public void addUserReward(UserReward userReward) {
        synchronized (this) {
            userRewards.add(userReward);
        }
    }

    public List<Provider> getTripDeals() {
        return Collections.unmodifiableList(tripDeals);
    }

    public void setTripDeals(List<Provider> tripDeals) {
        this.tripDeals = tripDeals;
    }

}
