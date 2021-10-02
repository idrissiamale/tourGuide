package com.mtracker.service;

import com.mtracker.dto.LocationDto;
import com.mtracker.dto.VisitedLocationDto;
import com.mtracker.model.User;
import com.mtracker.proxies.MicroserviceRewardsProxy;
import com.mtracker.proxies.MicroserviceUsersProxy;
import gpsUtil.GpsUtil;
import gpsUtil.location.VisitedLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;


@Service
public class TrackerServiceImpl implements TrackerService {
    private Logger logger = LoggerFactory.getLogger(TrackerServiceImpl.class);
    @Autowired
    private GpsUtil gpsUtil;

    @Autowired
    MicroserviceUsersProxy microserviceUsersProxy;

    @Autowired
    MicroserviceRewardsProxy microserviceRewardsProxy;


    private static final long trackingPollingInterval = TimeUnit.MINUTES.toSeconds(5);

    public TrackerServiceImpl(GpsUtil gpsUtil) {
        this.gpsUtil = gpsUtil;
    }

    @Async(value = "taskExecutorGpsUtil")
    @Override
    public CompletableFuture<VisitedLocationDto> trackUserLocation(User user) {
        VisitedLocationDto visitedLocationDto = getVisitedLocation(user);
        calculateRewards(user);
        return CompletableFuture.completedFuture(visitedLocationDto);
    }

    private void calculateRewards(User user) {
        microserviceRewardsProxy.calculateRewards(user.getUserName());
    }

    //public CompletableFuture<List<VisitedLocationDto>> trackUsersLocations() {
    //List<User> users = getAllUsers();
    //logger.info("Request to get a list of visitedLocations");
    //final List<VisitedLocationDto> visitedLocations = new ArrayList<>();
    //for (User user : users) {
    //VisitedLocationDto visitedLocation = trackUserLocation(user);
    //visitedLocations.add(new VisitedLocationDto(visitedLocation.getUserId(), visitedLocation.getLocationDto(), visitedLocation.getTimeVisited()));
    // }
    //return CompletableFuture.completedFuture(visitedLocations);
    //}


    private VisitedLocationDto getVisitedLocation(User user) {
        Locale locale = new Locale("en_US");
        Locale.setDefault(locale);
        VisitedLocation visitedLocation = gpsUtil.getUserLocation(user.getUserId());
        LocationDto locationDto = new LocationDto(visitedLocation.location.longitude, visitedLocation.location.latitude);
        VisitedLocationDto visitedLocationDto = new VisitedLocationDto(visitedLocation.userId, locationDto, visitedLocation.timeVisited);
        user.addToVisitedLocations(visitedLocationDto);
        return visitedLocationDto;
    }

    @Override
    public User getUser(String userName) {
        return microserviceUsersProxy.getUser(userName);
    }

    @Override
    public List<User> getAllUsers() {
        return microserviceUsersProxy.getAllUsers();
    }
}
