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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocationServiceImpl implements LocationService {
    private Logger logger = LoggerFactory.getLogger(LocationServiceImpl.class);

    private final MicroserviceUsersProxy microserviceUsersProxy;

    private final MicroserviceTrackerProxy microserviceTrackerProxy;

    @Autowired
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
    public VisitedLocationDto trackUserLocation(User user) {
        return microserviceTrackerProxy.trackUserLocation(user.getUserName());
    }

    @Override
    public User getUser(String userName) {
        return microserviceUsersProxy.getUser(userName);
    }

    @Override
    public List<User> getAllUsers() {
        return microserviceUsersProxy.getAllUsers();
    }

    @Override
    public List<CurrentLocationDto> getAllUsersCurrentLocations() {
        // TODO: Get a list of every user's most recent location as JSON
        //- Note: does not use gpsUtil to query for their current location,
        //        but rather gathers the user's current location from their stored location history.
        //
        // Return object should be the just a JSON mapping of userId to Locations similar to:
        //     {
        //        "019b04a9-067a-4c76-8817-ee75088c3822": {"longitude":-48.188821,"latitude":74.84371}
        //        ...
        //     }


        List<User> users = getAllUsers();
        List<CurrentLocationDto> usersCurrentLocations = new ArrayList<>();
        for (User user : users) {
            VisitedLocationDto currentUserLocation = latestVisitedLocation(user).get(0);
            usersCurrentLocations.add(new CurrentLocationDto(user.getUserId(), currentUserLocation.getLocationDto()));
        }
        return usersCurrentLocations;
    }

    private List<VisitedLocationDto> latestVisitedLocation(User user) {
        return user.getVisitedLocations()
                .stream()
                .filter(visitedLocationDto -> visitedLocationDto.getTimeVisited().equals(getLatestLocationTimestamp(user))).collect(Collectors.toList());
    }

    private Date getLatestLocationTimestamp(User user) {
        Date date = user.getVisitedLocation().getTimeVisited();
        List<Date> dates = new ArrayList<>();
        dates.add(date);
        return Collections.max(dates);
    }
}
