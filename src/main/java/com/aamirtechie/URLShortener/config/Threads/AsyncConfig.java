package com.aamirtechie.URLShortener.config.Threads;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean(name = "asyncExecutor")
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10000);
        executor.setMaxPoolSize(50000);
        executor.setQueueCapacity(50000);
        executor.setThreadNamePrefix("URLShortener-Async-");
        executor.initialize();
        return executor;
    }
}
