package com.mattraction.service;

import com.mattraction.dto.AttractionDto;
import com.mattraction.dto.LocationDto;
import com.mattraction.model.User;

import java.util.List;

public interface AttractionService {
    List<AttractionDto> getNearByAttractions(LocationDto location);

    List<AttractionDto> getAttractions();

    User getUser(String userName);

    LocationDto getLocation(String userName);
}
