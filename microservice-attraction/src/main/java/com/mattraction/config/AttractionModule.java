package com.mattraction.config;

import gpsUtil.GpsUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rewardCentral.RewardCentral;

/**
 * Configuration's class which permits to instantiate GpsUtil and RewardsCentral APIs and to use it throughout the application.
 *
 * @see gpsUtil.GpsUtil
 * @see rewardCentral.RewardCentral
 */
@Configuration
public class AttractionModule {
    @Bean
    public GpsUtil getGpsUtil() {
        return new GpsUtil();
    }

    @Bean
    public RewardCentral getRewardCentral() {
        return new RewardCentral();
    }
}
