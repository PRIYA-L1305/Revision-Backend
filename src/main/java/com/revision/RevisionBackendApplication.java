package com.revision;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RevisionBackendApplication {

    public static void main(String[] args) {

        SpringApplication.run(RevisionBackendApplication.class, args);
    }

}
