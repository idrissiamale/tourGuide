package com.mtrippricer.service;

import com.mtrippricer.model.User;
import tripPricer.Provider;

import java.util.List;

public interface TripPricerService {
    List<Provider> getTripDeals(User user);
}
