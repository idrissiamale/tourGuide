package com.muserlocation.proxies;

import com.muserlocation.dto.VisitedLocationDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "microservice-tracker", url = "localhost:8082")
public interface MicroserviceTrackerProxy {

    @GetMapping(value = "/trackUser")
    VisitedLocationDto trackUserLocation(@RequestParam("userName") String userName);
}
