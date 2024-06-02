package com.example.trainbooking.config;

import java.sql.Connection;
import java.sql.Statement;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableJpaRepositories(basePackages = "com.example.trainbookingsystem.repository")
public class DatabaseConfig {

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    public void loadData() {
        try (Connection connection = dataSource.getConnection(); Statement stmt = connection.createStatement()) {
            log.info("Inserting data...");
            
            stmt.execute("INSERT INTO train_detail (train_name, from_location, to_location) VALUES ('London to France Express', 'London', 'France')");
            stmt.execute("INSERT INTO train_detail (train_name, from_location, to_location) VALUES ('France to London Express', 'France', 'London')");

            stmt.execute("INSERT INTO train_section_and_seat_detail (train_number, section, ticket_cost, seat, is_seat_allotted) VALUES (1, 'Section_A', 5.25, 'A_1', false)");
            stmt.execute("INSERT INTO train_section_and_seat_detail (train_number, section, ticket_cost, seat, is_seat_allotted) VALUES (1, 'Section_A', 5.25, 'A_2', false)");
            stmt.execute("INSERT INTO train_section_and_seat_detail (train_number, section, ticket_cost, seat, is_seat_allotted) VALUES (1, 'Section_A', 5.25, 'A_3', false)");
            stmt.execute("INSERT INTO train_section_and_seat_detail (train_number, section, ticket_cost, seat, is_seat_allotted) VALUES (1, 'Section_A', 5.25, 'A_4', false)");
            stmt.execute("INSERT INTO train_section_and_seat_detail (train_number, section, ticket_cost, seat, is_seat_allotted) VALUES (1, 'Section_A', 5.25, 'A_5', false)");
            
            stmt.execute("INSERT INTO train_section_and_seat_detail (train_number, section, ticket_cost, seat, is_seat_allotted) VALUES (1, 'Section_B', 6.50, 'B_1', false)");
            stmt.execute("INSERT INTO train_section_and_seat_detail (train_number, section, ticket_cost, seat, is_seat_allotted) VALUES (1, 'Section_B', 6.50, 'B_2', false)");
            stmt.execute("INSERT INTO train_section_and_seat_detail (train_number, section, ticket_cost, seat, is_seat_allotted) VALUES (1, 'Section_B', 6.50, 'B_3', false)");
            stmt.execute("INSERT INTO train_section_and_seat_detail (train_number, section, ticket_cost, seat, is_seat_allotted) VALUES (1, 'Section_B', 6.50, 'B_4', false)");
            stmt.execute("INSERT INTO train_section_and_seat_detail (train_number, section, ticket_cost, seat, is_seat_allotted) VALUES (1, 'Section_B', 6.50, 'B_5', false)");

            stmt.execute("INSERT INTO train_section_and_seat_detail (train_number, section, ticket_cost, seat, is_seat_allotted) VALUES (2, 'Section_A', 7.25, 'A_1', false)");
            stmt.execute("INSERT INTO train_section_and_seat_detail (train_number, section, ticket_cost, seat, is_seat_allotted) VALUES (2, 'Section_A', 7.25, 'A_2', false)");
            stmt.execute("INSERT INTO train_section_and_seat_detail (train_number, section, ticket_cost, seat, is_seat_allotted) VALUES (2, 'Section_A', 7.25, 'A_3', false)");
            stmt.execute("INSERT INTO train_section_and_seat_detail (train_number, section, ticket_cost, seat, is_seat_allotted) VALUES (2, 'Section_A', 7.25, 'A_4', false)");
            stmt.execute("INSERT INTO train_section_and_seat_detail (train_number, section, ticket_cost, seat, is_seat_allotted) VALUES (2, 'Section_A', 7.25, 'A_5', false)");
            
            stmt.execute("INSERT INTO train_section_and_seat_detail (train_number, section, ticket_cost, seat, is_seat_allotted) VALUES (2, 'Section_B', 8.50, 'B_1', false)");
            stmt.execute("INSERT INTO train_section_and_seat_detail (train_number, section, ticket_cost, seat, is_seat_allotted) VALUES (2, 'Section_B', 8.50, 'B_2', false)");
            stmt.execute("INSERT INTO train_section_and_seat_detail (train_number, section, ticket_cost, seat, is_seat_allotted) VALUES (2, 'Section_B', 8.50, 'B_3', false)");
            stmt.execute("INSERT INTO train_section_and_seat_detail (train_number, section, ticket_cost, seat, is_seat_allotted) VALUES (2, 'Section_B', 8.50, 'B_4', false)");
            stmt.execute("INSERT INTO train_section_and_seat_detail (train_number, section, ticket_cost, seat, is_seat_allotted) VALUES (2, 'Section_B', 8.50, 'B_5', false)");

            stmt.execute("INSERT INTO user_detail (user_name, first_name, last_name, email, password) VALUES ('Hari_Haran_Cloud_Bees', 'Hari', 'Haran', 'hari.Haran@example.com', 'password')");
            stmt.execute("INSERT INTO user_detail (user_name, first_name, last_name, email, password) VALUES ('Rahul_Verma_Cloud_Bees', 'Rahul', 'Verma', 'rahul.verma@example.com', 'password123')");
            stmt.execute("INSERT INTO user_detail (user_name, first_name, last_name, email, password) VALUES ('Anjali_Sharma_Cloud_Bees', 'Anjali', 'Sharma', 'anjali.sharma@example.com', 'securepass')");
            stmt.execute("INSERT INTO user_detail (user_name, first_name, last_name, email, password) VALUES ('Vikas_Gupta_Cloud_Bees', 'Vikas', 'Gupta', 'vikas.gupta@example.com', 'mypass')");
            stmt.execute("INSERT INTO user_detail (user_name, first_name, last_name, email, password) VALUES ('Priya_Iyer_Cloud_Bees', 'Priya', 'Iyer', 'priya.iyer@example.com', 'priyapassword')");

            log.info("Inserting data completed");
        } catch (Exception e) {
            log.info("Failed to insert default values into the in-memory database.", e);
        }
    }

}
