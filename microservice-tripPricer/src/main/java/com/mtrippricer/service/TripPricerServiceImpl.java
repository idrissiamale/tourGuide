package com.mtrippricer.service;

import com.mtrippricer.dto.UserDto;
import com.mtrippricer.model.UserReward;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tripPricer.Provider;
import tripPricer.TripPricer;

import java.util.List;

@Service
public class TripPricerServiceImpl implements TripPricerService {
    private Logger logger = LoggerFactory.getLogger(TripPricerServiceImpl.class);
    private static final String tripPricerApiKey = "test-server-api-key";

    @Autowired
    private TripPricer tripPricer;

    public TripPricerServiceImpl(TripPricer tripPricer) {
        this.tripPricer = tripPricer;
    }

    @Override
    public List<Provider> getTripDeals(UserDto user) {
        int adults = user.getUserPreferences().getNumberOfAdults();
        int children = user.getUserPreferences().getNumberOfChildren();
        int nightsStay = user.getUserPreferences().getTripDuration();
        List<Provider> providers = tripPricer.getPrice(tripPricerApiKey, user.getUserId(), adults, children, nightsStay, getCumulativeRewardsPoints(user));
        user.setTripDeals(providers);
        return providers;
    }

    private int getCumulativeRewardsPoints(UserDto user) {
        return user.getUserRewards().stream().mapToInt(UserReward::getRewardPoints).sum();
    }
}
