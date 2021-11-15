package com.mattraction.proxies;

import com.mattraction.dto.LocationDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * An interface which permits to consume, with Feign client, the REST services exposed by UserLocation microservice.
 */
@FeignClient(name = "microservice-userLocation", url = "localhost:8083")
public interface MicroserviceUserLocationsProxy {

    /**
     * Mapping a GET request in order to fetch the user's last location.
     *
     * @param userName, method parameter which should be bound to the web request parameter.
     * @return the user's location.
     */
    @GetMapping(value = "/getLocation")
    LocationDto getLocation(@RequestParam("userName") String userName);
}
