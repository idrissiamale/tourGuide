package com.muserlocation.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.muserlocation.dto.CurrentLocationDto;
import com.muserlocation.dto.LocationDto;
import com.muserlocation.dto.VisitedLocationDto;
import com.muserlocation.model.User;
import com.muserlocation.service.LocationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = LocationController.class)
public class LocationControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper mapper;
    private User user;
    private User user2;
    private CopyOnWriteArrayList<User> users = new CopyOnWriteArrayList<>();
    private CopyOnWriteArrayList<CurrentLocationDto> currentLocations = new CopyOnWriteArrayList<>();

    @MockBean
    LocationService locationService;

    @BeforeEach
    public void setUp() {
        mapper = new ObjectMapper();
        user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
        user2 = new User(UUID.randomUUID(), "jon2", "000", "jon2@tourGuide.com");
    }

    @Test
    public void shouldReturn200WhenUserLocationIsFetched() throws Exception {
        VisitedLocationDto visitedLocationDto = new VisitedLocationDto(user.getUserId(), new LocationDto(-85.05112878, -180), new Date());
        user.addToVisitedLocations(visitedLocationDto);
        String jsonContent = mapper.writeValueAsString(visitedLocationDto.getLocationDto());
        when(locationService.getUser(user.getUserName())).thenReturn(user);
        when(locationService.getUserLocation(user)).thenReturn(visitedLocationDto);

        mockMvc
                .perform(get("/getLocation")
                        .param("userName", "jon")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk());

        verify(locationService).getUserLocation(user);
    }

    @Test
    public void shouldReturnUserLocationWhenItsFetched() throws Exception {
        Locale.setDefault(Locale.US);
        LocationDto location = new LocationDto(0.3017730176233755,  -108.9043206024857);
        VisitedLocationDto visitedLocationDto = new VisitedLocationDto(user.getUserId(), location, new Date());
        user.addToVisitedLocations(visitedLocationDto);
        String jsonContent = mapper.writeValueAsString(visitedLocationDto.getLocationDto());
        when(locationService.getUser(user.getUserName())).thenReturn(user);
        when(locationService.getUserLocation(user)).thenReturn(visitedLocationDto);

        MvcResult result = mockMvc
                .perform(get("/getLocation")
                        .param("userName", "jon")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk()).andReturn();

        verify(locationService).getUserLocation(user);
        LocationDto locationDto = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<LocationDto>() {
        });
        assertNotNull(locationDto);
    }

    @Test
    public void shouldReturn200WhenAllCurrentUsersLocationsAreFetched() throws Exception {
        generateUserLocationHistory(user);
        generateUserLocationHistory(user2);
        users.add(user);
        users.add(user2);

        for (User user : users) {
            VisitedLocationDto currentUserLocation = user.getLastVisitedLocation();
            currentLocations.add(new CurrentLocationDto(user.getUserId(), currentUserLocation.getLocationDto()));
        }
        String jsonContent = mapper.writeValueAsString(currentLocations);
        when(locationService.getAllUsersCurrentLocations()).thenReturn(currentLocations);

        mockMvc
                .perform(get("/getAllCurrentLocations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk());

        verify(locationService).getAllUsersCurrentLocations();
    }

    @Test
    public void shouldReturnAllCurrentUsersLocationsWhenTheyAreFetched() throws Exception {
        generateUserLocationHistory(user);
        generateUserLocationHistory(user2);
        users.add(user);
        users.add(user2);

        for (User user : users) {
            VisitedLocationDto currentUserLocation = user.getLastVisitedLocation();
            currentLocations.add(new CurrentLocationDto(user.getUserId(), currentUserLocation.getLocationDto()));
        }
        String jsonContent = mapper.writeValueAsString(currentLocations);
        when(locationService.getAllUsersCurrentLocations()).thenReturn(currentLocations);

        MvcResult result = mockMvc
                .perform(get("/getAllCurrentLocations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk()).andReturn();

        verify(locationService).getAllUsersCurrentLocations();
        CopyOnWriteArrayList<CurrentLocationDto> usersCurrentLocations = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<CopyOnWriteArrayList<CurrentLocationDto>>() {
        });
        assertNotNull(usersCurrentLocations);
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
