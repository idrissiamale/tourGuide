package com.mattraction.controller;

import com.mattraction.dto.AttractionDto;
import com.mattraction.dto.UserInfo;
import com.mattraction.model.User;
import com.mattraction.proxies.MicroserviceUsersProxy;
import com.mattraction.service.AttractionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Exposing Attraction's REST services to other microservices.
 *
 * @see com.mattraction.proxies.MicroserviceUsersProxy
 * @see com.mattraction.service.AttractionService
 */
@RestController
public class AttractionController {
    private Logger logger = LoggerFactory.getLogger(AttractionController.class);
    @Autowired
    MicroserviceUsersProxy microserviceUsersProxy;
    @Autowired
    private AttractionService attractionService;

    public AttractionController(MicroserviceUsersProxy microserviceUsersProxy, AttractionService attractionService) {
        this.microserviceUsersProxy = microserviceUsersProxy;
        this.attractionService = attractionService;
    }

    /**
     * Mapping a GET request in order to fetch the UserInfo DTO that contains, among others, the closest five tourist attractions to the user.
     *
     * @param userName, method parameter which should be bound to the web request parameter.
     * @return the UserInfo DTO.
     */
    @GetMapping(value = "/getNearbyAttractions")
    public UserInfo getNearbyAttractions(@RequestParam String userName) {
        User user = microserviceUsersProxy.getUser(userName);
        logger.info("User's nearby attractions found successfully.");
        return attractionService.getNearbyAttractions(user);
    }


    /**
     * Mapping a GET request in order to fetch tourist attractions.
     *
     * @return all tourist attractions saved in GpsUtil.
     */
    @GetMapping(value = "/getAllAttractions")
    public List<AttractionDto> getAllAttractions() {
        logger.info("TourGuide attractions found successfully.");
        return attractionService.getAttractions();
    }
}
