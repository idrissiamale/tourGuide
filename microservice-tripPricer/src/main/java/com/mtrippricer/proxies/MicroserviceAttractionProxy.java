package com.mtrippricer.proxies;


import com.mtrippricer.dto.AttractionDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "microservice-attraction", url = "localhost:8084")
public interface MicroserviceAttractionProxy {

    @GetMapping(value = "/getAllAttractions")
    List<AttractionDto> getAllAttractions();
}
