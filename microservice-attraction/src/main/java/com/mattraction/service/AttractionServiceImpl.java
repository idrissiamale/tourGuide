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

@Service
public class AttractionServiceImpl implements AttractionService {
    private Logger logger = LoggerFactory.getLogger(AttractionServiceImpl.class);
    private static final double STATUTE_MILES_PER_NAUTICAL_MILE = 1.15077945;
    @Autowired
    private  GpsUtil gpsUtil;

    @Autowired
    private RewardCentral rewardsCentral;

    @Autowired
    MicroserviceUserLocationsProxy microserviceUserLocationsProxy;

    public AttractionServiceImpl(GpsUtil gpsUtil, RewardCentral rewardsCentral, MicroserviceUserLocationsProxy microserviceUserLocationsProxy) {
        this.gpsUtil = gpsUtil;
        this.rewardsCentral = rewardsCentral;
        this.microserviceUserLocationsProxy = microserviceUserLocationsProxy;
    }

    @Override
    public UserInfo getNearbyAttractions(User user) {
        LocationDto userLocation = microserviceUserLocationsProxy.getLocation(user.getUserName());
        List<AttractionInfo> closestFiveAttractions = getTheClosestFiveAttractions(user, userLocation);
        logger.info("Successfully fetched the nearby attractions data for the following user : " + user.getUserName());
        return new UserInfo(userLocation.getLatitude(), userLocation.getLongitude(), closestFiveAttractions);
    }

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

    private List<AttractionInfo> getTheClosestFiveAttractions(User user, LocationDto userLocation) {
        return getAttractionsInfo(user, userLocation).stream().limit(5).collect(Collectors.toList());
    }

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

    private int getRewardPoints(AttractionDto attraction, User user) {
        return rewardsCentral.getAttractionRewardPoints(attraction.getAttractionId(), user.getUserId());
    }

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
