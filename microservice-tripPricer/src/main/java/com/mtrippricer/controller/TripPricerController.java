package com.mtrippricer.controller;

import com.mtrippricer.model.User;
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
    TripPricerService tripPricerService;


    @GetMapping(value = "/getTripDeals")
    public List<Provider> getTripDeals(@RequestParam String userName) {
        User user = tripPricerService.getUser(userName);
        return tripPricerService.getTripDeals(user);
    }


}
