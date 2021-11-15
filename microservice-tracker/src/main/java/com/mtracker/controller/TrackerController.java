package com.mtracker.controller;

import com.mtracker.dto.VisitedLocationDto;
import com.mtracker.model.User;
import com.mtracker.service.TrackerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Exposing the REST services of Tracker to other microservices.
 *
 * @see com.mtracker.service.TrackerService
 */
@RestController
public class TrackerController {
    @Autowired
    private TrackerService trackerService;

    public TrackerController(TrackerService trackerService) {
        this.trackerService = trackerService;
    }

    /**
     * Mapping a GET request in order to track the location for the user with the given name.
     *
     * @param userName, method parameter which should be bound to the web request parameter.
     * @return the new CompletableFuture with the user's last location.
     */
    @GetMapping(value = "/trackUser")
    public CompletableFuture<VisitedLocationDto> trackUserLocation(@RequestParam("userName") String userName) {
        User user = trackerService.getUser(userName);
        return trackerService.trackUserLocation(user);
    }
}
