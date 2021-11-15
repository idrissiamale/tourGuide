package com.mattraction.proxies;

import com.mattraction.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * A class which permits to consume, with Feign client, the REST services exposed by User microservice.
 */
@FeignClient(name = "microservice-user", url = "localhost:8081")
public interface MicroserviceUsersProxy {

    /**
     * Mapping a GET request in order to retrieve a user by its username.
     *
     * @param userName, method parameter which should be bound to the web request parameter.
     * @return the user with the given name.
     */
    @GetMapping("/getUser")
    User getUser(@RequestParam("userName") String userName);

    /**
     * Mapping a GET request in order to fetch TourGuide's users.
     *
     * @return a thread-safe ArrayList of users.
     */
    @GetMapping(value = "/getAllUsers")
    CopyOnWriteArrayList<User> getAllUsers();
}
