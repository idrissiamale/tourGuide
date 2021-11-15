package com.muserlocation.controller;

import com.muserlocation.dto.CurrentLocationDto;
import com.muserlocation.dto.LocationDto;
import com.muserlocation.dto.VisitedLocationDto;
import com.muserlocation.model.User;
import com.muserlocation.service.LocationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Exposing the REST services of Location to other microservices.
 *
 * @see com.muserlocation.service.LocationService
 */
@RestController
public class LocationController {
    private Logger logger = LoggerFactory.getLogger(LocationController.class);
    @Autowired
    private LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    /**
     * Mapping a GET request in order to fetch the location for the user with the given name.
     *
     * @param userName, method parameter which should be bound to the web request parameter.
     * @return the user location.
     */
    @GetMapping(value = "/getLocation")
    public LocationDto getLocation(@RequestParam("userName") String userName) {
        User user = locationService.getUser(userName);
        VisitedLocationDto visitedLocation = locationService.getUserLocation(user);
        logger.info("Successfully fetched the user location" );
        return visitedLocation.getLocationDto();
    }

    /**
     * Mapping a GET request in order to fetch the most recent location of the users.
     *
     * @return the most recent location of all users.
     */
    @GetMapping(value ="/getAllCurrentLocations")
    public CopyOnWriteArrayList<CurrentLocationDto> getAllCurrentLocations() {
        logger.info("Successfully fetched the users most recent location");
        return locationService.getAllUsersCurrentLocations();
    }
}
