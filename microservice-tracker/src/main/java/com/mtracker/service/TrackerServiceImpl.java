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

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;


@Service
public class TrackerServiceImpl implements TrackerService {
    private Logger logger = LoggerFactory.getLogger(TrackerServiceImpl.class);
    @Autowired
    private GpsUtil gpsUtil;

    @Autowired
    MicroserviceUsersProxy microserviceUsersProxy;

    @Autowired
    MicroserviceRewardsProxy microserviceRewardsProxy;

    public TrackerServiceImpl(GpsUtil gpsUtil, MicroserviceUsersProxy microserviceUsersProxy, MicroserviceRewardsProxy microserviceRewardsProxy) {
        this.gpsUtil = gpsUtil;
        this.microserviceUsersProxy = microserviceUsersProxy;
        this.microserviceRewardsProxy = microserviceRewardsProxy;
    }

    @Async(value = "taskExecutorGpsUtil")
    @Override
    public CompletableFuture<VisitedLocationDto> trackUserLocation(User user) {
        VisitedLocationDto visitedLocationDto = getVisitedLocation(user);
        microserviceRewardsProxy.calculateRewards(user.getUserName());
        return CompletableFuture.completedFuture(visitedLocationDto);
    }

    private VisitedLocationDto getVisitedLocation(User user) {
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
    public CopyOnWriteArrayList<User> getAllUsers() {
        return microserviceUsersProxy.getAllUsers();
    }
}
