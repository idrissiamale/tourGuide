package com.mtrippricer.service;

import com.mtrippricer.model.User;
import com.mtrippricer.model.UserReward;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tripPricer.Provider;
import tripPricer.TripPricer;

import java.util.List;

/**
 * Implementation of the TripPricerService interface.
 *
 * @see com.mtrippricer.service.TripPricerService
 */
@Service
public class TripPricerServiceImpl implements TripPricerService {
    private Logger logger = LoggerFactory.getLogger(TripPricerServiceImpl.class);
    private static final String tripPricerApiKey = "test-server-api-key";

    @Autowired
    private TripPricer tripPricer;

    public TripPricerServiceImpl(TripPricer tripPricer) {
        this.tripPricer = tripPricer;
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public List<Provider> getTripDeals(User user) {
        int adults = user.getUserPreferences().getNumberOfAdults();
        int children = user.getUserPreferences().getNumberOfChildren();
        int nightsStay = user.getUserPreferences().getTripDuration();
        List<Provider> providers = tripPricer.getPrice(tripPricerApiKey, user.getUserId(), adults, children, nightsStay, getCumulativeRewardsPoints(user));
        user.setTripDeals(providers);
        logger.info("The user's trip deals have been successfully fetched");
        return providers;
    }

    /**
     * Fetching the user's reward points and adding them up.
     *
     * @param user, it refers to the registered user.
     * @return the sum of the user's reward points.
     */
    private int getCumulativeRewardsPoints(User user) {
        return user.getUserRewards().stream().mapToInt(UserReward::getRewardPoints).sum();
    }
}
