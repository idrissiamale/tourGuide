package com.muserlocation.service;

import com.muserlocation.dto.CurrentLocationDto;
import com.muserlocation.dto.VisitedLocationDto;
import com.muserlocation.model.User;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * LocationService's role is to get the user location.
 */
public interface LocationService {
    /**
     * Fetching the last visited location from the stored location history.
     * If there is no history, the user is tracked to get his location.
     *
     * @param user, it refers to the registered user.
     * @return the last visited location.
     * @see com.muserlocation.model.User
     */
    VisitedLocationDto getUserLocation(User user);

    /**
     * Fetching the most recent location of the users.
     *
     * @return a thread-safe ArrayList of every user's most recent location.
     * @see com.muserlocation.model.User
     */
    CopyOnWriteArrayList<CurrentLocationDto> getAllUsersCurrentLocations();

    /**
     * Retrieves a user by its username. This user is fetched from User Microservice.
     *
     * @param userName, the given name.
     * @return the user with the given name.
     */
    User getUser(String userName);
}
