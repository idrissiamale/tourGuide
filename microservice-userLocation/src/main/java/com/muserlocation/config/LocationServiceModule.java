package com.muserlocation.config;

import gpsUtil.GpsUtil;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LocationServiceModule {
    @Bean
    public GpsUtil getGpsUtil() {
        return new GpsUtil();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
