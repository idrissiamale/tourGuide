package com.muserlocation.model;

import com.muserlocation.dto.VisitedLocationDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

@Getter
@Setter
@ToString
public class User {
    private final UUID userId;
    private final String userName;
    private String phoneNumber;
    private String emailAddress;
    private Date latestLocationTimestamp;
    private CopyOnWriteArrayList<VisitedLocationDto> visitedLocations = new CopyOnWriteArrayList<>();

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

    public VisitedLocationDto getLastVisitedLocation() {
        return visitedLocations.get(visitedLocations.size() - 1);
    }

    public VisitedLocationDto getVisitedLocation() {
        return visitedLocations.get(0);
    }
}
