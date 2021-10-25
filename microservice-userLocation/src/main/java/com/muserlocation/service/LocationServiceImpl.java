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

    @Override
    public VisitedLocationDto getUserLocation(User user) {
        return (user.getVisitedLocations().size() > 0) ?
                user.getLastVisitedLocation() :
                trackUserLocation(user);
    }

    @Override
    public CopyOnWriteArrayList<CurrentLocationDto> getAllUsersCurrentLocations() {
        CopyOnWriteArrayList<User> users = getAllUsers();
        CopyOnWriteArrayList<CurrentLocationDto> usersCurrentLocations = new CopyOnWriteArrayList<>();
        for (User user : users) {
            VisitedLocationDto currentUserLocation = user.getLastVisitedLocation();
            usersCurrentLocations.add(new CurrentLocationDto(user.getUserId(), currentUserLocation.getLocationDto()));
        }
        return usersCurrentLocations;
    }

    @Override
    public User getUser(String userName) {
        return microserviceUsersProxy.getUser(userName);
    }

    private VisitedLocationDto trackUserLocation(User user) {
        return microserviceTrackerProxy.trackUserLocation(user.getUserName());
    }

    private CopyOnWriteArrayList<User> getAllUsers() {
        return microserviceUsersProxy.getAllUsers();
    }
}
