package com.muserlocation.service;

import com.muserlocation.dto.LocationDto;
import com.muserlocation.dto.VisitedLocationDto;
import com.muserlocation.model.User;
import com.muserlocation.proxies.MicroserviceTrackerProxy;
import com.muserlocation.proxies.MicroserviceUsersProxy;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LocationServiceTest {
    private LocationServiceImpl locationService;
    private User user;

    @Autowired
    MicroserviceUsersProxy microserviceUsersProxy;

    @Autowired
    MicroserviceTrackerProxy microserviceTrackerProxy;

    @BeforeEach
    public void setUp() {
        Locale.setDefault(Locale.US);
        locationService = new LocationServiceImpl(microserviceUsersProxy, microserviceTrackerProxy);
        user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
    }


    @Test
    public void getUserLocation() {
        LocationDto location = new LocationDto(-85.05112878, -180);
        user.addToVisitedLocations(new VisitedLocationDto(user.getUserId(), location, new Date()));

        VisitedLocationDto userLocation = locationService.getUserLocation(user);
        System.out.println(user.getVisitedLocations().get(0).getLocationDto().getLatitude());

        assertEquals(userLocation, user.getLastVisitedLocation());
    }


    @Test
    public void trackUser() {
        VisitedLocationDto visitedLocation = locationService.trackUserLocation(user);
        System.out.println(visitedLocation);

        assertEquals(user.getUserId(), visitedLocation.getUserId());
    }


    @Test
    public void highVolumeTrackLocation() {
        List<User> allUsers = locationService.getAllUsers();

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        for (User user : allUsers) {
            locationService.trackUserLocation(user);
        }
        stopWatch.stop();

        System.out.println("highVolumeTrackLocation: Time Elapsed: " + TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()) + " seconds.");
        assertTrue(TimeUnit.MINUTES.toSeconds(15) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
    }

}
