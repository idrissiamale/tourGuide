package com.mtrippricer.service;

import com.mtrippricer.dto.UserDto;
import tripPricer.Provider;

import java.util.List;

public interface TripPricerService {
    List<Provider> getTripDeals(UserDto user);
}
