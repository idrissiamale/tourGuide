package com.mrewards.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Configuration's class which defines a ThreadPoolTaskExecutor in order to manage the application's threads.
 */
@Configuration
@EnableAsync
public class AsyncConfig {
    @Bean(name = "taskExecutorRewardsPoint")
    public Executor taskExecutorReward() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(70);
        executor.setMaxPoolSize(80);
        executor.setQueueCapacity(1000);
        executor.setKeepAliveSeconds(60);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setThreadNamePrefix("rewardsPointThread-");
        executor.initialize();
        return executor;
    }
}
