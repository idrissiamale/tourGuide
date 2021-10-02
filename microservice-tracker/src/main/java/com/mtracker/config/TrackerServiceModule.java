package com.mtracker.config;

import gpsUtil.GpsUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TrackerServiceModule {
    @Bean
    public GpsUtil getGpsUtil() {
        return new GpsUtil();
    }
}
