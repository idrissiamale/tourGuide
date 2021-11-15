package com.mrewards.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rewardCentral.RewardCentral;

/**
 * Configuration's class which permits to instantiate RewardsCentral API in order to use it throughout the application.
 *
 * @see rewardCentral.RewardCentral
 */
@Configuration
public class RewardsModule {

    @Bean
    public RewardCentral getRewardCentral() {
        return new RewardCentral();
    }

}
