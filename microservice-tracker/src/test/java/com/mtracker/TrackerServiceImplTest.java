package com.mtracker;

import com.mtracker.model.User;
import com.mtracker.proxies.MicroserviceRewardsProxy;
import com.mtracker.proxies.MicroserviceUsersProxy;
import com.mtracker.service.TrackerServiceImpl;
import gpsUtil.GpsUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Locale;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TrackerServiceImplTest {
    private TrackerServiceImpl trackerService;
    private User user;

    @Mock
    MicroserviceRewardsProxy microserviceRewardsProxy;

    @Mock
    MicroserviceUsersProxy microserviceUsersProxy;

    @BeforeEach
    public void setUp() {
        Locale.setDefault(Locale.US);
        GpsUtil gpsUtil = new GpsUtil();
        trackerService = new TrackerServiceImpl(gpsUtil, microserviceUsersProxy, microserviceRewardsProxy);
        user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
    }

    @Test
    public void shouldTrackUserLocation() {
        trackerService.trackUserLocation(user);

        assertEquals(1, user.getVisitedLocations().size());
    }
}
