package com.mattraction.proxies;

import com.mattraction.dto.LocationDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "microservice-userLocation", url = "localhost:8083")
public interface MicroserviceUserLocationsProxy {
    @GetMapping(value = "/getLocation")
    LocationDto getLocation(@RequestParam("userName") String userName);
}
