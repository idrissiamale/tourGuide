package com.mtrippricer.controller;

import com.mtrippricer.model.User;
import com.mtrippricer.proxies.MicroserviceUsersProxy;
import com.mtrippricer.service.TripPricerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tripPricer.Provider;

import java.util.List;

/**
 * Exposing the REST services of TripPricer to other microservices.
 *
 * @see com.mtrippricer.proxies.MicroserviceUsersProxy
 */
@RestController
public class TripPricerController {
    private Logger logger = LoggerFactory.getLogger(TripPricerController.class);
    @Autowired
    MicroserviceUsersProxy microserviceUsersProxy;
    @Autowired
    private TripPricerService tripPricerService;

    public TripPricerController(MicroserviceUsersProxy microserviceUsersProxy, TripPricerService tripPricerService) {
        this.microserviceUsersProxy = microserviceUsersProxy;
        this.tripPricerService = tripPricerService;
    }

    /**
     * Mapping a GET request in order to fetch trip deals for the user with the given name.
     *
     * @param userName, method parameter which should be bound to the web request parameter.
     * @return trip deals for the user with the given name.
     */
    @GetMapping(value = "/getTripDeals")
    public List<Provider> getTripDeals(@RequestParam String userName) {
        User user = microserviceUsersProxy.getUser(userName);
        logger.info("Trip deals have been successfully fetched");
        return tripPricerService.getTripDeals(user);
    }
}
