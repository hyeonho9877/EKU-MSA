package com.hyunho9877.freeboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FreeBoardApplication {

    public static void main(String[] args) {
        SpringApplication.run(FreeBoardApplication.class, args);
    }

}
