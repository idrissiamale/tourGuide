package com.mrewards.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrewards.dto.AttractionDto;
import com.mrewards.dto.LocationDto;
import com.mrewards.dto.VisitedLocationDto;
import com.mrewards.model.User;
import com.mrewards.model.UserReward;
import com.mrewards.service.RewardsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = RewardsController.class)
public class RewardsControllerTest {
    private ObjectMapper mapper;
    private User user;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RewardsService rewardsService;

    @BeforeEach
    public void setUp() {
        mapper = new ObjectMapper();
        user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
    }

    @Test
    @DisplayName("Checking that the controller returns status code 200 and that the method is asynchronous when calculateRewards is invoked")
    public void shouldReturn200AndAsyncStartedTrueWhenCalculateRewardsIsCorrectlyInvoked() throws Exception {
        user.addToVisitedLocations(new VisitedLocationDto(user.getUserId(), new LocationDto(33.817595, -117.922008), new Date()));
        when(rewardsService.getUser(user.getUserName())).thenReturn(user);
        when(rewardsService.calculateRewards(user)).thenReturn(CompletableFuture.completedFuture(null));

        MvcResult mvcResult = this.mockMvc.perform(get("/calculateRewards")
                .param("userName", "jon")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(request().asyncStarted())
                .andDo(print())
                .andReturn();

        this.mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk());

        verify(rewardsService).calculateRewards(user);
    }

    @Test
    @DisplayName("Checking that the controller returns status code 200 when the user has received his rewards")
    public void shouldReturn200WhenRewardPointsHasBeenSubmitted() throws Exception {
        user.addToVisitedLocations(new VisitedLocationDto(user.getUserId(), new LocationDto(33.817595, -117.922008), new Date()));
        user.addUserReward(new UserReward(user.getVisitedLocations().get(0),new AttractionDto("Disneyland", "Anaheim", "CA", 33.817595D, -117.922008), 5));
        when(rewardsService.getUser(user.getUserName())).thenReturn(user);
        when(rewardsService.getUserRewards(user)).thenReturn(user.getUserRewards());
        String jsonContent = mapper.writeValueAsString(user.getUserRewards());

        MvcResult result = mockMvc
                .perform(get("/getRewards").contentType(MediaType.APPLICATION_JSON)
                        .param("userName", "jon")
                        .content(jsonContent))
                .andExpect(status().isOk()).andReturn();

        verify(rewardsService).getUserRewards(user);
        List<UserReward> userRewards = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<UserReward>>() {
        });
        assertEquals(1, userRewards.size());
    }
}
