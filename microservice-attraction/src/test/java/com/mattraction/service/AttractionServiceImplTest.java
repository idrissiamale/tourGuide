package com.mattraction.service;

import com.mattraction.dto.AttractionDto;
import com.mattraction.dto.UserInfo;
import com.mattraction.model.User;
import com.mattraction.proxies.MicroserviceUserLocationsProxy;
import gpsUtil.GpsUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("Checking that we get all tourist attractions saved in GpsUtil API")
    public void shouldReturnAllAttractions() {
        List<AttractionDto> attractions = attractionService.getAttractions();

        assertEquals(26, attractions.size());
    }

    @Test
    @DisplayName("Comparing expected name and actual name to check that the tourist attractions are correctly fetched")
    public void shouldReturnTheSameName() {
        List<AttractionDto> attractions = attractionService.getAttractions();

        assertEquals("Disneyland", attractions.get(0).getAttractionName());
    }

    @Test
    @DisplayName("Checking that the closest five attractions to the user are correctly fetched by comparing their size")
    public void shouldReturnTheClosestFiveAttractions() {
        AttractionDto attraction = attractionService.getAttractions().get(0);
        when(microserviceUserLocationsProxy.getLocation(user.getUserName())).thenReturn(attraction);

        UserInfo userInfo = attractionService.getNearbyAttractions(user);

        assertEquals(5, userInfo.getNearbyAttractions().size());
    }

    @Test
    @DisplayName("Comparing expected name of Disneyland (the first attraction of the list) and the actual name to check that the closest five attractions are correctly fetched")
    public void shouldReturnTheClosestAttractionOfFive() {
        AttractionDto attraction = attractionService.getAttractions().get(0);
        when(microserviceUserLocationsProxy.getLocation(user.getUserName())).thenReturn(attraction);

        UserInfo userInfo = attractionService.getNearbyAttractions(user);

        assertEquals("Disneyland", userInfo.getNearbyAttractions().get(0).getName());
    }

    @Test
    @DisplayName("Comparing expected distance of Disneyland (the first attraction of the list) and the actual distance to check that the closest five attractions are correctly fetched")
    public void shouldReturnTheSameDistance() {
        AttractionDto attraction = attractionService.getAttractions().get(0);
        when(microserviceUserLocationsProxy.getLocation(user.getUserName())).thenReturn(attraction);

        UserInfo userInfo = attractionService.getNearbyAttractions(user);

        assertEquals(0.0, userInfo.getNearbyAttractions().get(0).getDistance());
    }
}
