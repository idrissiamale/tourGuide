package com.mtracker;

import com.mtracker.controller.TrackerController;
import com.mtracker.dto.LocationDto;
import com.mtracker.dto.VisitedLocationDto;
import com.mtracker.model.User;
import com.mtracker.service.TrackerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Date;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TrackerController.class)
public class TrackerControllerTest {
    private User user;
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TrackerService trackerService;

    @BeforeEach
    public void setUp() {
        Locale.setDefault(Locale.US);
        user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
    }

    @Test
    public void shouldReturn200AndAsyncStartedTrueAndUserVisitedLocationWhenTrackUserLocationIsCorrectlyInvoked() throws Exception {
        VisitedLocationDto visitedLocation = new VisitedLocationDto(user.getUserId(), new LocationDto(33.817595, -117.922008), new Date());
        user.addToVisitedLocations(visitedLocation);
        when(trackerService.getUser(user.getUserName())).thenReturn(user);
        when(trackerService.trackUserLocation(user)).thenReturn(CompletableFuture.completedFuture(visitedLocation));

        MvcResult mvcResult = this.mockMvc.perform(get("/trackUser")
                .param("userName", "jon")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(request().asyncStarted())
                .andDo(print())
                .andReturn();

        this.mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk());

        verify(trackerService).trackUserLocation(user);
    }
}
