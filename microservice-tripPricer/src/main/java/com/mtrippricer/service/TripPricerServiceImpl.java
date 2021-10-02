package com.mtrippricer.service;

import com.mtrippricer.model.User;
import com.mtrippricer.model.UserReward;
import com.mtrippricer.proxies.MicroserviceUsersProxy;
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

    @Autowired
    MicroserviceUsersProxy microserviceUsersProxy;

    public TripPricerServiceImpl(TripPricer tripPricer) {
        this.tripPricer = tripPricer;
    }

    @Override
    public List<Provider> getTripDeals(User user) {
        List<Provider> providers = tripPricer.getPrice(tripPricerApiKey, user.getUserId(), user.getUserPreferences().getNumberOfAdults(),
                user.getUserPreferences().getNumberOfChildren(), user.getUserPreferences().getTripDuration(), getCumulativeRewardsPoints(user));
        user.setTripDeals(providers);
        return providers;
    }

    @Override
    public User getUser(String userName) {
        return microserviceUsersProxy.getUser(userName);
    }

    private int getCumulativeRewardsPoints(User user) {
        return user.getUserRewards().stream().mapToInt(UserReward::getRewardPoints).sum();
    }
}
