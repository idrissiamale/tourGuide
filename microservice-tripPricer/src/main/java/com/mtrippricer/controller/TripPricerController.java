package com.mtrippricer.controller;

import com.mtrippricer.dto.UserDto;
import com.mtrippricer.proxies.MicroserviceUsersProxy;
import com.mtrippricer.service.TripPricerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tripPricer.Provider;

import java.util.List;

@RestController
public class TripPricerController {
    @Autowired
    MicroserviceUsersProxy microserviceUsersProxy;
    @Autowired
    TripPricerService tripPricerService;


    public TripPricerController(MicroserviceUsersProxy microserviceUsersProxy, TripPricerService tripPricerService) {
        this.microserviceUsersProxy = microserviceUsersProxy;
        this.tripPricerService = tripPricerService;
    }

    @GetMapping(value = "/getTripDeals")
    public List<Provider> getTripDeals(@RequestParam String userName) {
        UserDto user = microserviceUsersProxy.getUser(userName);
        return tripPricerService.getTripDeals(user);
    }
}
