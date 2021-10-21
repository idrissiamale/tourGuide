package com.muserlocation.proxies;

import com.muserlocation.config.FeignExceptionConfig;
import com.muserlocation.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.concurrent.CopyOnWriteArrayList;

@FeignClient(name = "microservice-user", url = "localhost:8081", configuration = {FeignExceptionConfig.class})
public interface MicroserviceUsersProxy {

    @GetMapping(value = "/getUser", produces = "application/json")
    User getUser(@RequestParam("userName") String userName);

    @GetMapping(value = "/getAllUsers")
    CopyOnWriteArrayList<User> getAllUsers();
}
