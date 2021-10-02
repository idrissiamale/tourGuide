package com.mattraction.proxies;

import com.mattraction.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "microservice-user", url = "localhost:8081")
public interface MicroserviceUsersProxy {
    @GetMapping("/getUser")
    User getUser(@RequestParam("userName") String userName);

    @GetMapping(value = "/getAllUsers")
    List<User> getAllUsers();
}
