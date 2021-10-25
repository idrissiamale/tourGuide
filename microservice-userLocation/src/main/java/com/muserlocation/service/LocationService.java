package com.muserlocation.service;

import com.muserlocation.dto.CurrentLocationDto;
import com.muserlocation.dto.VisitedLocationDto;
import com.muserlocation.model.User;

import java.util.concurrent.CopyOnWriteArrayList;

public interface LocationService {
    VisitedLocationDto getUserLocation(User user);

    CopyOnWriteArrayList<CurrentLocationDto> getAllUsersCurrentLocations();

    User getUser(String userName);
}
