package com.mtracker.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * An interface which permits to consume, with Feign client, the REST services exposed by Rewards microservice.
 */
@FeignClient(name = "microservice-rewards", url = "${mrewards.url}")
public interface MicroserviceRewardsProxy {

    /**
     * Mapping a GET request in order to calculate rewards for the user with the given username.
     *
     * @param userName, method parameter which should be bound to the web request parameter.
     */
    @GetMapping(value = "/calculateRewards")
    void calculateRewards(@RequestParam("userName") String userName);
}
