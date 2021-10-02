package com.mtracker.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "microservice-rewards", url = "http://127.0.0.1:8085")
public interface MicroserviceRewardsProxy {

    @GetMapping(value = "/calculateRewards")
    void calculateRewards(@RequestParam("userName") String userName);
}
