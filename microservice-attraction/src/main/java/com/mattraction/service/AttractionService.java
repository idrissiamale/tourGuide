package com.mattraction.service;

import com.mattraction.dto.AttractionDto;
import com.mattraction.dto.UserInfo;
import com.mattraction.model.User;

import java.util.concurrent.CopyOnWriteArrayList;

public interface AttractionService {
    UserInfo getNearbyAttractions(User user);

    CopyOnWriteArrayList<AttractionDto> getAttractions();
}
