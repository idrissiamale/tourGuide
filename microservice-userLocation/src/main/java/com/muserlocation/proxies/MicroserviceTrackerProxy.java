package com.muserlocation.proxies;

import com.muserlocation.dto.VisitedLocationDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * An interface which permits to consume, with Feign client, the REST services exposed by Tracker microservice.
 */
@FeignClient(name = "microservice-tracker", url = "localhost:8082")
public interface MicroserviceTrackerProxy {

    /**
     * Mapping a GET request in order to track the location for the user with the given name.
     *
     * @param userName, method parameter which should be bound to the web request parameter.
     * @return the location of the user with the given name.
     */
    @GetMapping(value = "/trackUser")
    VisitedLocationDto trackUserLocation(@RequestParam("userName") String userName);
}
