package com.mattraction;

import com.mattraction.dto.AttractionDto;
import com.mattraction.dto.UserInfo;
import com.mattraction.model.User;
import com.mattraction.proxies.MicroserviceUserLocationsProxy;
import com.mattraction.service.AttractionServiceImpl;
import gpsUtil.GpsUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rewardCentral.RewardCentral;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AttractionServiceImplTest {
    private AttractionServiceImpl attractionService;
    private User user;

    @Mock
    private MicroserviceUserLocationsProxy microserviceUserLocationsProxy;


    @BeforeEach
    public void setUp() {
        GpsUtil gpsUtil = new GpsUtil();
        RewardCentral rewardCentral = new RewardCentral();
        attractionService = new AttractionServiceImpl(gpsUtil, rewardCentral, microserviceUserLocationsProxy);
        user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
    }


    @Test
    public void shouldReturnAllAttractions() {
        List<AttractionDto> attractions = attractionService.getAttractions();

        assertEquals(26, attractions.size());
    }

    @Test
    public void shouldReturnTheSameName() {
        List<AttractionDto> attractions = attractionService.getAttractions();

        assertEquals("Disneyland", attractions.get(0).getAttractionName());
    }

    @Test
    public void shouldReturnTheClosestFiveAttractions() {
        AttractionDto attraction = attractionService.getAttractions().get(0);
        when(microserviceUserLocationsProxy.getLocation(user.getUserName())).thenReturn(attraction);

        UserInfo userInfo = attractionService.getNearbyAttractions(user);
        System.out.println(userInfo);

        assertEquals(5, userInfo.getNearbyAttractions().size());
    }

    @Test
    public void shouldReturnTheClosestAttractionOfFive() {
        AttractionDto attraction = attractionService.getAttractions().get(0);
        when(microserviceUserLocationsProxy.getLocation(user.getUserName())).thenReturn(attraction);

        UserInfo userInfo = attractionService.getNearbyAttractions(user);

        assertEquals("Disneyland", userInfo.getNearbyAttractions().get(0).getName());
    }

    @Test
    public void shouldReturnTheSameDistance() {
        AttractionDto attraction = attractionService.getAttractions().get(0);
        when(microserviceUserLocationsProxy.getLocation(user.getUserName())).thenReturn(attraction);

        UserInfo userInfo = attractionService.getNearbyAttractions(user);

        assertEquals(0.0, userInfo.getNearbyAttractions().get(0).getDistance());
    }
}
