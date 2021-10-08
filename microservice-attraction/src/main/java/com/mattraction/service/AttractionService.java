package com.mattraction.service;

import com.mattraction.dto.AttractionDto;
import com.mattraction.dto.UserInfo;
import com.mattraction.model.User;

import java.util.List;

public interface AttractionService {
    UserInfo getNearbyAttractions(User user);

    List<AttractionDto> getAttractions();

    User getUser(String userName);
}
