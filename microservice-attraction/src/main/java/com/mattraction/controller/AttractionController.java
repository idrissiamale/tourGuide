package com.mattraction.controller;

import com.mattraction.dto.AttractionDto;
import com.mattraction.dto.LocationDto;
import com.mattraction.model.User;
import com.mattraction.service.AttractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AttractionController {
    @Autowired
    AttractionService attractionService;

    public AttractionController(AttractionService attractionService) {
        this.attractionService = attractionService;
    }

    //  TODO: Change this method to no longer return a List of Attractions.
    //  Instead: Get the closest five tourist attractions to the user - no matter how far away they are.
    //  Return a new JSON object that contains:
    // Name of Tourist attraction,
    // Tourist attractions lat/long,
    // The user's location lat/long,
    // The distance in miles between the user's location and each of the attractions.
    // The reward points for visiting each Attraction.
    //    Note: Attraction reward points can be gathered from RewardsCentral
    @GetMapping(value = "/getNearbyAttractions")
    public List<AttractionDto> getNearbyAttractions(@RequestParam String userName) {
        User user = attractionService.getUser(userName);
        LocationDto locationDto = attractionService.getLocation(user.getUserName());
        return attractionService.getNearByAttractions(locationDto);
    }


    @GetMapping(value = "/getAllAttractions")
    public List<AttractionDto> getAllAttractions() {
        return attractionService.getAttractions();
    }
}
