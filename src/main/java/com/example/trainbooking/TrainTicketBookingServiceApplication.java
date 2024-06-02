package com.example.trainbooking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.trainbooking.repository")
public class TrainTicketBookingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrainTicketBookingServiceApplication.class, args);
    }
}
