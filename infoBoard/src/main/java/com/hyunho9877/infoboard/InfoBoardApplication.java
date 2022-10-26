package com.hyunho9877.infoboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class InfoBoardApplication {

    public static void main(String[] args) {
        SpringApplication.run(InfoBoardApplication.class, args);
    }

}