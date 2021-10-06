package com.mtrippricer;

import com.mtrippricer.model.User;
import com.mtrippricer.service.TripPricerServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import tripPricer.Provider;
import tripPricer.TripPricer;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TripPricerServiceImplTest {
    private TripPricerServiceImpl tripPricerServiceImpl;

    @BeforeEach
    public void setUp() {
        TripPricer tripPricer = new TripPricer();
        tripPricerServiceImpl = new TripPricerServiceImpl(tripPricer);
    }

    @Test
    public void getTripDeals() {
        User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");

        List<Provider> providers = tripPricerServiceImpl.getTripDeals(user);

        assertEquals(10, providers.size());
    }
}
