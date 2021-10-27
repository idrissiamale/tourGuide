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

@RestController
public class TrackerController {
    @Autowired
    private TrackerService trackerService;

    public TrackerController(TrackerService trackerService) {
        this.trackerService = trackerService;
    }

    @GetMapping(value = "/trackUser")
    public CompletableFuture<VisitedLocationDto> trackUserLocation(@RequestParam("userName") String userName) throws ExecutionException, InterruptedException {
        User user = trackerService.getUser(userName);
        return trackerService.trackUserLocation(user);
    }
}
