package com.mattraction.service;

import com.mattraction.dto.AttractionDto;
import com.mattraction.dto.UserInfo;
import com.mattraction.model.User;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * AttractionService's role is to send tourist attractions recommendations to the user.
 */
public interface AttractionService {
    /**
     * Fetching the closest five tourist attractions to the user - no matter how far away they are.
     *
     * @param user, it refers to the registered user.
     * @return it returns a new JSON object, UserInfo DTO that contains:
     * - name of Tourist attraction,
     * - Tourist attractions latitude/longitude,
     * - The user's location latitude/longitude,
     * - The distance in miles between the user's location and each of the attractions.
     * - The reward points for visiting each Attraction.
     * @see com.mattraction.model.User
     * @see com.mattraction.dto.UserInfo
     */
    UserInfo getNearbyAttractions(User user);

    /**
     * Fetching TourGuide's tourist attractions from GpsUtil API.
     *
     * @return a thread-safe ArrayList of attractions.
     */
    CopyOnWriteArrayList<AttractionDto> getAttractions();
}
