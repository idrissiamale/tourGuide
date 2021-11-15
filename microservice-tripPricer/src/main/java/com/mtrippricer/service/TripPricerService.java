package com.mtrippricer.service;

import com.mtrippricer.model.User;
import tripPricer.Provider;

import java.util.List;

/**
 * TripPricerService's role is to get the user trip deals.
 */
public interface TripPricerService {
    /**
     * Fetching trip deals relevant to the travel preferences of the user.
     *
     * @param user, it refers to the registered user.
     * @return a list of trip deals.
     * @see com.mtrippricer.model.User
     */
    List<Provider> getTripDeals(User user);
}
