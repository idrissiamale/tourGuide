package com.muserlocation.model;

import com.muserlocation.dto.VisitedLocationDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * A model class which gathers user's id, name, phone number, email but also the locations he visited and the date and time of his visits.
 */
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

    public VisitedLocationDto getLastVisitedLocation() {
        return getVisitedLocations()
                .stream()
                .filter(visitedLocationDto -> visitedLocationDto.getTimeVisited().equals(getLatestLocationTimestamp())).findFirst().get();
    }

    public Date getLatestLocationTimestamp() {
        List<Date> dates = getVisitedLocations().stream().map(VisitedLocationDto::getTimeVisited).collect(Collectors.toList());
        return Collections.max(dates);
    }
}
