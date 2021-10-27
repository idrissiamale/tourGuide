package com.mtracker.service;

import com.mtracker.dto.VisitedLocationDto;
import com.mtracker.model.User;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;

public interface TrackerService {
    CompletableFuture<VisitedLocationDto> trackUserLocation(User user) throws ExecutionException, InterruptedException;

    User getUser(String userName);

    CopyOnWriteArrayList<User> getAllUsers();

    //void trackAllUsers();

    //CompletableFuture<List<VisitedLocationDto>> trackUsersLocations();
}
