package com.mattraction;

import com.mattraction.dto.AttractionDto;
import com.mattraction.dto.LocationDto;
import com.mattraction.model.User;
import com.mattraction.proxies.MicroserviceUserLocationsProxy;
import com.mattraction.proxies.MicroserviceUsersProxy;
import com.mattraction.service.AttractionServiceImpl;
import gpsUtil.GpsUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class AttractionServiceImplTest {
    private AttractionServiceImpl attractionService;

    @Autowired
    private GpsUtil gpsUtil;
    private User user;
    @Autowired
    private MicroserviceUsersProxy microserviceUsersProxy;

    @Autowired
    private MicroserviceUserLocationsProxy microserviceUserLocationsProxy;


    @BeforeEach
    public void setUp() {
        Locale.setDefault(Locale.US);
        gpsUtil = new GpsUtil();
        attractionService = new AttractionServiceImpl(gpsUtil, rewardsCentral);
        user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
    }


    @Test
    public void getUserLocation() {
        LocationDto locationDto = new LocationDto( -116.603649,  34.761573);
        System.out.println(locationDto);

        List<AttractionDto> attractions = attractionService.getNearByAttractions(locationDto);

        System.out.println(attractions);

    }
}
