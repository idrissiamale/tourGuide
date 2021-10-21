package com.mtrippricer.proxies;

import com.mtrippricer.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "microservice-user", url = "localhost:8081")
public interface MicroserviceUsersProxy {
    @GetMapping("/getUser")
    UserDto getUser(@RequestParam("userName") String userName);

    @GetMapping(value = "/getAllUsers")
    List<UserDto> getAllUsers();
}
