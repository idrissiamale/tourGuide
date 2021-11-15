package com.mattraction.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mattraction.dto.AttractionDto;
import com.mattraction.dto.AttractionInfo;
import com.mattraction.dto.UserInfo;
import com.mattraction.model.User;
import com.mattraction.proxies.MicroserviceUsersProxy;
import com.mattraction.service.AttractionService;
import gpsUtil.GpsUtil;
import gpsUtil.location.Attraction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AttractionController.class)
public class AttractionControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper mapper;
    private User user;
    private GpsUtil gpsUtil;
    List<AttractionInfo> nearbyAttractions = new ArrayList<>();

    @MockBean
    MicroserviceUsersProxy microserviceUsersProxy;

    @MockBean
    AttractionService attractionService;

    @BeforeEach
    public void setUpPerTest() {
        AttractionInfo attractionInfo1 = new AttractionInfo("Disneyland", 33.817595, -117.922008, 0.0, 849);
        nearbyAttractions.add(attractionInfo1);
        AttractionInfo attractionInfo2 = new AttractionInfo("San Diego Zoo", 32.735317, -117.149048, 87.0344172215767, 803);
        nearbyAttractions.add(attractionInfo2);
        AttractionInfo attractionInfo3 = new AttractionInfo("Joshua Tree National Park", 33.881866, -115.90065, 115.99464245531166, 149);
        nearbyAttractions.add(attractionInfo3);
        AttractionInfo attractionInfo4 = new AttractionInfo("Mojave National Preserve", 35.141689, -115.510399, 164.91310606752234, 815);
        nearbyAttractions.add(attractionInfo4);
        AttractionInfo attractionInfo5 = new AttractionInfo("Kartchner Caverns State Park", 31.837551, -110.347382, 460.1199036169892, 554);
        nearbyAttractions.add(attractionInfo5);
        user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
        mapper = new ObjectMapper();
        gpsUtil = new GpsUtil();
    }

    @Test
    @DisplayName("Checking that the controller returns status code 200 when the UserInfo DTO was correctly fetched")
    public void shouldReturn200WhenUserIsFoundAndUserInfoExists() throws Exception {
        UserInfo userInfo = new UserInfo(33.817595, 117.922008, nearbyAttractions);
        String jsonContent = mapper.writeValueAsString(userInfo);
        when(microserviceUsersProxy.getUser(user.getUserName())).thenReturn(user);
        when(attractionService.getNearbyAttractions(user)).thenReturn(userInfo);

        mockMvc
                .perform(get("/getNearbyAttractions")
                        .param("userName", "jon")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk());

        verify(attractionService).getNearbyAttractions(user);
    }

    @Test
    @DisplayName("Checking that the controller returns the closest five attractions to the user when user's name exists")
    public void shouldReturnUserInfoWhichContainsTheClosestFiveAttractionsWhenUserIsFound() throws Exception {
        UserInfo userInfo = new UserInfo(33.817595, 117.922008, nearbyAttractions);
        String jsonContent = mapper.writeValueAsString(userInfo);
        when(microserviceUsersProxy.getUser(user.getUserName())).thenReturn(user);
        when(attractionService.getNearbyAttractions(user)).thenReturn(userInfo);

        MvcResult result = mockMvc
                .perform(get("/getNearbyAttractions")
                        .param("userName", "jon")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk()).andReturn();

        verify(attractionService).getNearbyAttractions(user);
        UserInfo userInfo1 = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<UserInfo>() {
        });
        assertNotNull(userInfo1);
    }

    @Test
    @DisplayName("Checking that the controller returns status code 200 when the tourist attractions are found")
    public void shouldReturn200WhenAttractionsAreFound() throws Exception {
        List<Attraction> attractions = gpsUtil.getAttractions();
        CopyOnWriteArrayList<AttractionDto> attractionDtoList = new CopyOnWriteArrayList<>();
        for (Attraction attraction : attractions) {
            attractionDtoList.add(new AttractionDto(attraction.latitude, attraction.longitude, attraction.attractionName, attraction.city, attraction.state, attraction.attractionId));
        }
        String jsonContent = mapper.writeValueAsString(attractionDtoList);
        when(attractionService.getAttractions()).thenReturn(attractionDtoList);

        mockMvc
                .perform(get("/getAllAttractions").contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk());

        verify(attractionService).getAttractions();
    }

    @Test
    @DisplayName("Checking that the tourist attractions from GpsUtil API are correctly fetched by the controller")
    public void shouldReturnAllAttractionsWhenTheyAreFound() throws Exception {
        List<Attraction> attractions = gpsUtil.getAttractions();
        CopyOnWriteArrayList<AttractionDto> attractionDtoList = new CopyOnWriteArrayList<>();
        for (Attraction attraction : attractions) {
            attractionDtoList.add(new AttractionDto(attraction.latitude, attraction.longitude, attraction.attractionName, attraction.city, attraction.state, attraction.attractionId));
        }
        String jsonContent = mapper.writeValueAsString(attractionDtoList);
        when(attractionService.getAttractions()).thenReturn(attractionDtoList);

        MvcResult result = mockMvc
                .perform(get("/getAllAttractions").contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk()).andReturn();

        verify(attractionService).getAttractions();
        List<AttractionDto> attractionList = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<AttractionDto>>() {
        });
        assertEquals(26, attractionList.size());
    }

}
