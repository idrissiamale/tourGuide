package com.mattraction.controller;

import com.mattraction.dto.AttractionDto;
import com.mattraction.dto.UserInfo;
import com.mattraction.model.User;
import com.mattraction.service.AttractionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AttractionController {
    private Logger logger = LoggerFactory.getLogger(AttractionController.class);
    @Autowired
    AttractionService attractionService;

    public AttractionController(AttractionService attractionService) {
        this.attractionService = attractionService;
    }

    @GetMapping(value = "/getNearbyAttractions")
    public UserInfo getNearbyAttractions(@RequestParam String userName) {
        User user = attractionService.getUser(userName);
        logger.info("User's nearby attractions found successfully.");
        return attractionService.getNearbyAttractions(user);
    }


    @GetMapping(value = "/getAllAttractions")
    public List<AttractionDto> getAllAttractions() {
        logger.info("TourGuide attractions found successfully.");
        return attractionService.getAttractions();
    }
}
