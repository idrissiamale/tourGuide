package com.mrewards.model;

import com.mrewards.dto.VisitedLocationDto;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * User model class with id, name, phone number, email, the locations he visited and his reward points as fields.
 */
@Getter
@ToString
public class User {
    private final UUID userId;
    private final String userName;
    private String phoneNumber;
    private String emailAddress;
    private CopyOnWriteArrayList<VisitedLocationDto> visitedLocations = new CopyOnWriteArrayList<>();
    private CopyOnWriteArrayList<UserReward> userRewards = new CopyOnWriteArrayList<>();

    public User(UUID userId, String userName, String phoneNumber, String emailAddress) {
        this.userId = userId;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
    }

    public CopyOnWriteArrayList<VisitedLocationDto> getVisitedLocations() {
        return visitedLocations;
    }

    public void addToVisitedLocations(VisitedLocationDto visitedLocation) {
        visitedLocations.add(visitedLocation);
    }

    public void clearVisitedLocations() {
        visitedLocations.clear();
    }

    public CopyOnWriteArrayList<UserReward> getUserRewards() {
        return userRewards;
    }

    public void addUserReward(UserReward userReward) {
        userRewards.add(userReward);
    }
}
