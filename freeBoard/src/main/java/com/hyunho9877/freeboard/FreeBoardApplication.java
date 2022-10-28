package com.hyunho9877.freeboard;

import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@RefreshScope
@SpringBootApplication
@EnableJpaAuditing
public class FreeBoardApplication {

    public static void main(String[] args) {
        SpringApplication.run(FreeBoardApplication.class, args);
    }

    @Bean
    TimedAspect timedAspect(MeterRegistry registry) {
        return new TimedAspect(registry);
    }

}
