package com.mtrippricer.controller;

import com.mtrippricer.dto.UserDto;
import com.mtrippricer.service.TripPricerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tripPricer.Provider;

import java.util.List;

@RestController
public class TripPricerController {
    @Autowired
    TripPricerServiceImpl tripPricerService;


    @GetMapping(value = "/getTripDeals")
    public List<Provider> getTripDeals(@RequestParam String userName) {
        UserDto user = tripPricerService.getUser(userName);
        return tripPricerService.getTripDeals(user);
    }
}
