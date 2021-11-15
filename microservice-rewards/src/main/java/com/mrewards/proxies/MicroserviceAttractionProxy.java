package com.mrewards.proxies;

import com.mrewards.dto.AttractionDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * An interface which permits to consume, with Feign client, the REST services exposed by Attraction microservice.
 */
@FeignClient(name = "microservice-attraction", url = "localhost:8084")
public interface MicroserviceAttractionProxy {

    /**
     * Mapping a GET request in order to fetch tourist attractions.
     *
     * @return a thread-safe ArrayList of attractions.
     */
    @GetMapping(value = "/getAllAttractions")
    CopyOnWriteArrayList<AttractionDto> getAllAttractions();
}
