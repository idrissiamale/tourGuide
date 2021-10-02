package com.mattraction.service;

import com.mattraction.dto.AttractionDto;
import com.mattraction.dto.LocationDto;
import com.mattraction.model.User;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public interface AttractionService {
    List<AttractionDto> getNearByAttractions(LocationDto location) throws ExecutionException, InterruptedException;

    CompletableFuture<List<AttractionDto>> getAttractions();

    User getUser(String userName);

    LocationDto getLocation(String userName);
}
