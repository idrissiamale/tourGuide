package com.mtrippricer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mtrippricer.model.User;
import com.mtrippricer.proxies.MicroserviceUsersProxy;
import com.mtrippricer.service.TripPricerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tripPricer.Provider;
import tripPricer.TripPricer;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TripPricerController.class)
public class TripPricerControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper mapper;
    private TripPricer tripPricer;

    @MockBean
    MicroserviceUsersProxy microserviceUsersProxy;

    @MockBean
    TripPricerService tripPricerService;

    @BeforeEach
    public void setUpPerTest() {
        tripPricer = new TripPricer();
        mapper = new ObjectMapper();
    }

    @Test
    @DisplayName("Checking that the controller returns status code 200 and trip deals for the user with the given name")
    public void shouldReturn200AndTripDealsWhenUserIsFound() throws Exception {
        User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
        String tripPricerApiKey = "test-server-api-key";
        int adults = 1;
        int children = 2;
        int nightsStay = 3;
        List<Provider> providers = tripPricer.getPrice(tripPricerApiKey, user.getUserId(), adults, children, nightsStay, 5);
        user.setTripDeals(providers);
        String jsonContent = mapper.writeValueAsString(providers);
        when(microserviceUsersProxy.getUser(user.getUserName())).thenReturn(user);
        when(tripPricerService.getTripDeals(user)).thenReturn(providers);

        mockMvc
                .perform(get("/getTripDeals")
                        .param("userName", "jon")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk());

        verify(tripPricerService).getTripDeals(user);
    }
}
