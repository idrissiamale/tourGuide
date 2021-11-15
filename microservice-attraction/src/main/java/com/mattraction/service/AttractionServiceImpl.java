package com.mattraction.service;

import com.mattraction.dto.AttractionDto;
import com.mattraction.dto.AttractionInfo;
import com.mattraction.dto.LocationDto;
import com.mattraction.dto.UserInfo;
import com.mattraction.model.User;
import com.mattraction.proxies.MicroserviceUserLocationsProxy;
import gpsUtil.GpsUtil;
import gpsUtil.location.Attraction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rewardCentral.RewardCentral;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * Implementation of the AttractionService interface.
 *
 * @see com.mattraction.service.AttractionService
 */
@Service
public class AttractionServiceImpl implements AttractionService {
    private Logger logger = LoggerFactory.getLogger(AttractionServiceImpl.class);
    private static final double STATUTE_MILES_PER_NAUTICAL_MILE = 1.15077945;
    @Autowired
    private GpsUtil gpsUtil;

    @Autowired
    private RewardCentral rewardsCentral;

    @Autowired
    MicroserviceUserLocationsProxy microserviceUserLocationsProxy;

    public AttractionServiceImpl(GpsUtil gpsUtil, RewardCentral rewardsCentral, MicroserviceUserLocationsProxy microserviceUserLocationsProxy) {
        this.gpsUtil = gpsUtil;
        this.rewardsCentral = rewardsCentral;
        this.microserviceUserLocationsProxy = microserviceUserLocationsProxy;
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public UserInfo getNearbyAttractions(User user) {
        LocationDto userLocation = microserviceUserLocationsProxy.getLocation(user.getUserName());
        List<AttractionInfo> closestFiveAttractions = getTheClosestFiveAttractions(user, userLocation);
        logger.info("Successfully fetched the nearby attractions data for the following user : " + user.getUserName());
        return new UserInfo(userLocation.getLatitude(), userLocation.getLongitude(), closestFiveAttractions);
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public CopyOnWriteArrayList<AttractionDto> getAttractions() {
        List<Attraction> attractions = gpsUtil.getAttractions();
        CopyOnWriteArrayList<AttractionDto> attractionDtoList = new CopyOnWriteArrayList<>();
        for (Attraction attraction : attractions) {
            attractionDtoList.add(new AttractionDto(attraction.latitude, attraction.longitude, attraction.attractionName, attraction.city, attraction.state, attraction.attractionId));
        }
        logger.info("Successfully fetched attractions.");
        return attractionDtoList;
    }

    /**
     * Fetching the closest five attractions to the user from the attractions list sorted by distance.
     *
     * @param userLocation, it refers to the last location of the user.
     * @param user,         it refers to the registered user.
     * @return the closest five tourist attractions to the user.
     */
    private List<AttractionInfo> getTheClosestFiveAttractions(User user, LocationDto userLocation) {
        return getAttractionsInfo(user, userLocation).stream().limit(5).collect(Collectors.toList());
    }

    /**
     * Fetching a list of tourist attractions sorted by distance.
     *
     * @param userLocation, it refers to the last location of the user.
     * @param user,         it refers to the registered user.
     * @return a list of tourist attractions sorted by their distance from the last user location.
     */
    private List<AttractionInfo> getAttractionsInfo(User user, LocationDto userLocation) {
        List<AttractionInfo> attractionInfoList = new ArrayList<>();
        double distance;
        for (AttractionDto attraction : getAttractions()) {
            distance = getDistance(userLocation, attraction);
            attractionInfoList.add(new AttractionInfo(attraction.getAttractionName(), attraction.getLatitude(), attraction.getLongitude(), distance, getRewardPoints(attraction, user)));
        }
        attractionInfoList.sort(Comparator.comparingDouble(AttractionInfo::getDistance));
        return attractionInfoList;
    }

    /**
     * Fetching reward points from RewardsCentral API.
     *
     * @param attraction, it refers to a tourist attraction fetched from GpsUtil API.
     * @param user,       it refers to the registered user.
     * @return the user's reward points.
     */
    private int getRewardPoints(AttractionDto attraction, User user) {
        return rewardsCentral.getAttractionRewardPoints(attraction.getAttractionId(), user.getUserId());
    }

    /**
     * Method which calculates the distance between two locations. The formula used here to find the distance between them is The Haversine Formula.
     *
     * @param loc1, latitude/longitude of the first location.
     * @param loc2, latitude/longitude of the second location compared with the first to get the distance between them.
     * @return the distance between two locations in miles.
     */
    private double getDistance(LocationDto loc1, LocationDto loc2) {
        double lat1 = Math.toRadians(loc1.getLatitude());
        double lon1 = Math.toRadians(loc1.getLongitude());
        double lat2 = Math.toRadians(loc2.getLatitude());
        double lon2 = Math.toRadians(loc2.getLongitude());

        double angle = Math.acos(Math.sin(lat1) * Math.sin(lat2)
                + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));

        double nauticalMiles = 60 * Math.toDegrees(angle);
        return STATUTE_MILES_PER_NAUTICAL_MILE * nauticalMiles;
    }
}
