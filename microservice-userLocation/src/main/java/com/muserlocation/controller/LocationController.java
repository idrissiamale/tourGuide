package com.muserlocation.controller;

import com.muserlocation.dto.CurrentLocationDto;
import com.muserlocation.dto.LocationDto;
import com.muserlocation.dto.VisitedLocationDto;
import com.muserlocation.model.User;
import com.muserlocation.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CopyOnWriteArrayList;

@RestController
public class LocationController {
    @Autowired
    private LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping(value = "/getLocation")
    public LocationDto getLocation(@RequestParam("userName") String userName) {
        User user = locationService.getUser(userName);
        VisitedLocationDto visitedLocation = locationService.getUserLocation(user);
        return visitedLocation.getLocationDto();
    }

    @GetMapping(value ="/getAllCurrentLocations")
    public CopyOnWriteArrayList<CurrentLocationDto> getAllCurrentLocations() {
        return locationService.getAllUsersCurrentLocations();
    }

}
