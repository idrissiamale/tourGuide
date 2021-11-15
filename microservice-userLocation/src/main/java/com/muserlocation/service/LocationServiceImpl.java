package com.muserlocation.service;

import com.muserlocation.dto.CurrentLocationDto;
import com.muserlocation.dto.VisitedLocationDto;
import com.muserlocation.model.User;
import com.muserlocation.proxies.MicroserviceTrackerProxy;
import com.muserlocation.proxies.MicroserviceUsersProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Implementation of the LocationService interface.
 *
 * @see com.muserlocation.proxies.MicroserviceUsersProxy
 * @see com.muserlocation.proxies.MicroserviceTrackerProxy
 * @see com.muserlocation.service.LocationService
 */
@Service
public class LocationServiceImpl implements LocationService {
    private Logger logger = LoggerFactory.getLogger(LocationServiceImpl.class);

    @Autowired
    MicroserviceUsersProxy microserviceUsersProxy;

    @Autowired
    MicroserviceTrackerProxy microserviceTrackerProxy;

    public LocationServiceImpl(MicroserviceUsersProxy microserviceUsersProxy, MicroserviceTrackerProxy microserviceTrackerProxy) {
        this.microserviceUsersProxy = microserviceUsersProxy;
        this.microserviceTrackerProxy = microserviceTrackerProxy;
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public VisitedLocationDto getUserLocation(User user) {
        logger.info("Successfully fetched the user location");
        return (user.getVisitedLocations().size() > 0) ?
                user.getLastVisitedLocation() :
                trackUserLocation(user);
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public CopyOnWriteArrayList<CurrentLocationDto> getAllUsersCurrentLocations() {
        CopyOnWriteArrayList<User> users = getAllUsers();
        CopyOnWriteArrayList<CurrentLocationDto> usersCurrentLocations = new CopyOnWriteArrayList<>();
        for (User user : users) {
            VisitedLocationDto currentUserLocation = user.getLastVisitedLocation();
            usersCurrentLocations.add(new CurrentLocationDto(user.getUserId(), currentUserLocation.getLocationDto()));
        }
        logger.info("Successfully fetched the users most recent location");
        return usersCurrentLocations;
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
     * A method which tracks the user location.
     *
     * @param user, it refers to the registered user.
     * @return the user location after tracking.
     * @see com.muserlocation.model.User
     * @see com.muserlocation.proxies.MicroserviceTrackerProxy
     */
    private VisitedLocationDto trackUserLocation(User user) {
        logger.info("Successfully tracked the location of the following user : " + user.getUserName());
        return microserviceTrackerProxy.trackUserLocation(user.getUserName());
    }

    /**
     * Fetching users from User Microservice.
     *
     * @return a thread-safe ArrayList of users.
     */
    private CopyOnWriteArrayList<User> getAllUsers() {
        logger.info("The users have been successfully fetched");
        return microserviceUsersProxy.getAllUsers();
    }
}
