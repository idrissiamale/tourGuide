package com.mtracker.config;

import gpsUtil.GpsUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration's class which permits to instantiate GpsUtil API in order to use it throughout the application.
 *
 * @see gpsUtil.GpsUtil
 */
@Configuration
public class TrackerModule {
    @Bean
    public GpsUtil getGpsUtil() {
        return new GpsUtil();
    }
}
