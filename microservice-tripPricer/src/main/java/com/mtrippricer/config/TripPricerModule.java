package com.mtrippricer.config;

import com.fasterxml.jackson.databind.Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.jackson.datatype.money.MoneyModule;
import tripPricer.TripPricer;

/**
 * Configuration's class which permits to instantiate TripPricer and MoneyModule APIs in order to use it throughout the application.
 *
 * @see tripPricer.TripPricer
 */
@Configuration
public class TripPricerModule {
    @Bean
    public TripPricer getTripPricer() {
        return new TripPricer();
    }

    @Bean
    public Module moneyModule() {
        return new MoneyModule();
    }
}
