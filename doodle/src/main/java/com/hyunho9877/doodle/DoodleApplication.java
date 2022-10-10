package com.hyunho9877.doodle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DoodleApplication {

    public static void main(String[] args) {
        SpringApplication.run(DoodleApplication.class, args);
    }

}
