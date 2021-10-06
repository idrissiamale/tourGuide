package com.mattraction.service;

import com.mattraction.dto.AttractionDto;
import com.mattraction.dto.AttractionInfo;
import com.mattraction.dto.LocationDto;
import com.mattraction.dto.UserInfo;
import com.mattraction.model.User;
import com.mattraction.proxies.MicroserviceUserLocationsProxy;
import com.mattraction.proxies.MicroserviceUsersProxy;
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
import java.util.stream.Collectors;

@Service
public class AttractionServiceImpl implements AttractionService {
    private Logger logger = LoggerFactory.getLogger(AttractionServiceImpl.class);
    private static final double STATUTE_MILES_PER_NAUTICAL_MILE = 1.15077945;
    private final GpsUtil gpsUtil;

    @Autowired
    MicroserviceUsersProxy microserviceUsersProxy;

    @Autowired
    MicroserviceUserLocationsProxy microserviceUserLocationsProxy;

    @Autowired
    private RewardCentral rewardsCentral;

    public AttractionServiceImpl(GpsUtil gpsUtil, RewardCentral rewardsCentral) {
        this.gpsUtil = gpsUtil;
        this.rewardsCentral = rewardsCentral;
    }

    @Override
    public UserInfo getNearbyAttractions(User user) {
        List<AttractionInfo> closestFiveAttractions = getFiveClosestAttractions(user);
        LocationDto userLocation = getLocation(user.getUserName());
        return new UserInfo(userLocation.getLatitude(), userLocation.getLongitude(), closestFiveAttractions);
    }

    @Override
    public List<AttractionDto> getAttractions() {
        List<Attraction> attractions = gpsUtil.getAttractions();
        List<AttractionDto> attractionDtoList = new ArrayList<>();
        for (Attraction attraction : attractions) {
            attractionDtoList.add(new AttractionDto(attraction.longitude, attraction.latitude, attraction.attractionName, attraction.city, attraction.state, attraction.attractionId));
        }
        return attractionDtoList;
    }


    @Override
    public User getUser(String userName) {
        return microserviceUsersProxy.getUser(userName);
    }

    @Override
    public LocationDto getLocation(String userName) {
        return microserviceUserLocationsProxy.getLocation(userName);
    }

    private List<AttractionInfo> getFiveClosestAttractions(User user) {
        return getAttractionInfo(user).stream().limit(5).collect(Collectors.toList());
    }

    private List<AttractionInfo> getAttractionInfo(User user) {
        List<AttractionInfo> attractionInfoList = new ArrayList<>();
        LocationDto userLocation = getLocation(user.getUserName());
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
