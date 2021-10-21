package com.mtrippricer;

import com.mtrippricer.dto.UserDto;
import com.mtrippricer.model.UserPreferences;
import com.mtrippricer.service.TripPricerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tripPricer.Provider;
import tripPricer.TripPricer;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TripPricerServiceImplTest {
    private TripPricerServiceImpl tripPricerServiceImpl;
    private TripPricer tripPricer;

    @BeforeEach
    public void setUp() {
        tripPricer = new TripPricer();
        tripPricerServiceImpl = new TripPricerServiceImpl(tripPricer);
    }

    @Test
    public void getTripDeals() {
        UserDto user = new UserDto(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");

        List<Provider> providers = tripPricerServiceImpl.getTripDeals(user);

        assertEquals(5, providers.size());
    }

    @Test
    public void checkerPreferencesUser() {
        UserPreferences userPreferences = new UserPreferences();
        userPreferences.setNumberOfChildren(3);
        userPreferences.setTripDuration(1);
        UserDto user = new UserDto(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com", userPreferences);

        List<Provider> providers = tripPricerServiceImpl.getTripDeals(user);

        System.out.println(providers.get(0).price);
    }
}
