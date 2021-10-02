package com.mattraction.service;

import com.mattraction.dto.AttractionDto;
import com.mattraction.dto.LocationDto;
import com.mattraction.model.User;
import com.mattraction.proxies.MicroserviceUserLocationsProxy;
import com.mattraction.proxies.MicroserviceUsersProxy;
import gpsUtil.GpsUtil;
import gpsUtil.location.Attraction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public AttractionServiceImpl(GpsUtil gpsUtil) {
        this.gpsUtil = gpsUtil;
    }


    @Override
    public List<AttractionDto> getNearByAttractions(LocationDto location) {
        List<AttractionDto> nearbyAttractions = new ArrayList<>();
        for (AttractionDto attraction : getAttractions()) {
            if (isWithinAttractionProximity(attraction, location)) {
                nearbyAttractions.add(attraction);
            }
        }
        return nearbyAttractions;
    }

    public List<AttractionDto> getGetNearByAttractions(LocationDto location) {
        return getAttractions()
                .stream()
                .filter(attractionDto -> isWithinAttractionProximity(attractionDto, location))
                .collect(Collectors.toList());
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

    private boolean isWithinAttractionProximity(AttractionDto attraction, LocationDto location) {
        // proximity in miles
        int attractionProximityRange = 200;
        return !(getDistance(attraction, location) > attractionProximityRange);
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
