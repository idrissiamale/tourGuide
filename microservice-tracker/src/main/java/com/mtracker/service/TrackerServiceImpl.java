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

/**
 * Implementation of the TrackerService interface.
 *
 * @see com.mtracker.proxies.MicroserviceRewardsProxy
 * @see com.mtracker.proxies.MicroserviceUsersProxy
 * @see com.mtracker.service.TrackerService
 */
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

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Async(value = "taskExecutorGpsUtil")
    @Override
    public CompletableFuture<VisitedLocationDto> trackUserLocation(User user) {
        VisitedLocationDto visitedLocationDto = getVisitedLocation(user);
        microserviceRewardsProxy.calculateRewards(user.getUserName());
        logger.info("Successfully tracked the location of the following user : " + user.getUserName());
        return CompletableFuture.completedFuture(visitedLocationDto);
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public User getUser(String userName) {
        logger.info("Successfully fetched the user with the following name : " + userName);
        return microserviceUsersProxy.getUser(userName);
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public CopyOnWriteArrayList<User> getAllUsers() {
        logger.info("The users have been successfully fetched");
        return microserviceUsersProxy.getAllUsers();
    }

    /**
     * Tracking user location with GpsUtil API and adding it to his location history.
     *
     * @param user, it refers to the registered user.
     * @return the last location visited by the user.
     */
    private VisitedLocationDto getVisitedLocation(User user) {
        VisitedLocation visitedLocation = gpsUtil.getUserLocation(user.getUserId());
        LocationDto locationDto = new LocationDto(visitedLocation.location.longitude, visitedLocation.location.latitude);
        VisitedLocationDto visitedLocationDto = new VisitedLocationDto(visitedLocation.userId, locationDto, visitedLocation.timeVisited);
        user.addToVisitedLocations(visitedLocationDto);
        return visitedLocationDto;
    }
}
