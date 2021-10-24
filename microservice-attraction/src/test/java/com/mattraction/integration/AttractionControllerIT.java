package com.mattraction.integration;

import com.mattraction.controller.AttractionController;
import com.mattraction.dto.AttractionDto;
import com.mattraction.proxies.MicroserviceUsersProxy;
import com.mattraction.service.AttractionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AttractionControllerIT {
    private AttractionController attractionController;
    @Autowired
    MicroserviceUsersProxy microserviceUsersProxy;
    @Autowired
    private AttractionService attractionService;

    @BeforeEach
    public void setUp() {
        attractionController = new AttractionController(microserviceUsersProxy, attractionService);
    }

    @Test
    public void shouldReturnAllAttractions() {
        List<AttractionDto> attractions = attractionController.getAllAttractions();
        assertEquals(26, attractions.size());
    }
}
