package com.muserlocation.service;

import com.muserlocation.dto.CurrentLocationDto;
import com.muserlocation.dto.LocationDto;
import com.muserlocation.dto.VisitedLocationDto;
import com.muserlocation.model.User;
import com.muserlocation.proxies.MicroserviceTrackerProxy;
import com.muserlocation.proxies.MicroserviceUsersProxy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LocationServiceImplTest {
    private LocationServiceImpl locationServiceImpl;
    private User user;
    private User user2;
    private User user3;
    private CopyOnWriteArrayList<User> users = new CopyOnWriteArrayList<>();
    private CopyOnWriteArrayList<CurrentLocationDto> currentLocations = new CopyOnWriteArrayList<>();

    @Mock
    MicroserviceUsersProxy microserviceUsersProxy;

    @Mock
    MicroserviceTrackerProxy microserviceTrackerProxy;

    @BeforeEach
    public void setUp() {
        Locale.setDefault(Locale.US);
        locationServiceImpl = new LocationServiceImpl(microserviceUsersProxy, microserviceTrackerProxy);
        user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
        user2 = new User(UUID.randomUUID(), "jon2", "000", "jon2@tourGuide.com");
        user3 = new User(UUID.randomUUID(), "jon3", "000", "jon3@tourGuide.com");
    }


    @Test
    public void shouldGetTheLastVisitedLocationWhenUserHasVisitedLocationsHistory() {
        LocationDto location = new LocationDto(-85.05112878, -180);
        user.addToVisitedLocations(new VisitedLocationDto(user.getUserId(), location, new Date()));

        VisitedLocationDto userLocation = locationServiceImpl.getUserLocation(user);

        assertEquals(userLocation, user.getLastVisitedLocation());
    }

    @Test
    public void shouldTrackUserLocationWhenUserDoesNotHaveVisitedLocationsHistory() {
        LocationDto location = new LocationDto(-85.05112878, -180);
        VisitedLocationDto visitedLocation = new VisitedLocationDto(user.getUserId(), location, new Date());
        when(microserviceTrackerProxy.trackUserLocation(user.getUserName())).thenReturn(visitedLocation);

        VisitedLocationDto userLocation = locationServiceImpl.getUserLocation(user);

        assertEquals(userLocation, visitedLocation);
    }

    @Test
    public void shouldReturnAllCurrentUsersLocations() {
        generateUserLocationHistory(user);
        generateUserLocationHistory(user2);
        generateUserLocationHistory(user3);
        users.add(user);
        users.add(user2);
        users.add(user3);
        when(microserviceUsersProxy.getAllUsers()).thenReturn(users);

        for (User user : users) {
            VisitedLocationDto currentUserLocation = user.getLastVisitedLocation();
            currentLocations.add(new CurrentLocationDto(user.getUserId(), currentUserLocation.getLocationDto()));
        }

        CopyOnWriteArrayList<CurrentLocationDto> usersCurrentLocations = locationServiceImpl.getAllUsersCurrentLocations();

        assertEquals(currentLocations.get(0).getLocationDto(), usersCurrentLocations.get(0).getLocationDto());
    }

    @Test
    public void shouldReturnTheMostRecentUserLocation() {
        generateUserLocationHistory(user);
        generateUserLocationHistory(user2);
        generateUserLocationHistory(user3);
        users.add(user);
        users.add(user2);
        users.add(user3);
        when(microserviceUsersProxy.getAllUsers()).thenReturn(users);

        for (User user : users) {
            VisitedLocationDto currentUserLocation = user.getLastVisitedLocation();
            currentLocations.add(new CurrentLocationDto(user.getUserId(), currentUserLocation.getLocationDto()));
        }

        CopyOnWriteArrayList<CurrentLocationDto> usersCurrentLocations = locationServiceImpl.getAllUsersCurrentLocations();

        assertEquals(user.getLastVisitedLocation().getLocationDto(), usersCurrentLocations.get(0).getLocationDto());
    }

    private void generateUserLocationHistory(User user) {
        IntStream.range(0, 3).forEach(i -> {
            user.addToVisitedLocations(new VisitedLocationDto(user.getUserId(), new LocationDto(generateRandomLatitude(), generateRandomLongitude()), getRandomTime()));
        });
    }

    private double generateRandomLongitude() {
        double leftLimit = -180;
        double rightLimit = 180;
        return leftLimit + new Random().nextDouble() * (rightLimit - leftLimit);
    }

    private double generateRandomLatitude() {
        double leftLimit = -85.05112878;
        double rightLimit = 85.05112878;
        return leftLimit + new Random().nextDouble() * (rightLimit - leftLimit);
    }

    private Date getRandomTime() {
        LocalDateTime localDateTime = LocalDateTime.now().minusDays(new Random().nextInt(30));
        return Date.from(localDateTime.toInstant(ZoneOffset.UTC));
    }
}
