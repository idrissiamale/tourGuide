package com.mattraction.config;

import gpsUtil.GpsUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rewardCentral.RewardCentral;

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
