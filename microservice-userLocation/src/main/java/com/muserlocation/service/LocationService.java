package com.muserlocation.service;

import com.muserlocation.dto.CurrentLocationDto;
import com.muserlocation.dto.VisitedLocationDto;
import com.muserlocation.model.User;

import java.util.List;

public interface LocationService {
    VisitedLocationDto getUserLocation(User user);

    VisitedLocationDto trackUserLocation(User user);

    List<CurrentLocationDto> getAllUsersCurrentLocations();

    User getUser(String userName);

    List<User> getAllUsers();
}
