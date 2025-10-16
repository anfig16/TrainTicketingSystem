//Created by Andrea Figueroa and Luis Nery
package com.group3.ticketing.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the Spring Boot Ticketing Project application.
 * 
 * This class bootstraps the Spring Boot application by invoking
 * SpringApplication.run(), which sets up the application context,
 * initializes all beans, and starts the embedded web server.
 */
@SpringBootApplication // Annotation that marks this class as the configuration and bootstrap class
public class ProjectApplication {

    /**
     * The main method that starts the Spring Boot application.
     *
     * @param args Command-line arguments passed when starting the application.
     */
    public static void main(String[] args) {
        // Launches the Spring Boot application using the ProjectApplication class as the source
        SpringApplication.run(ProjectApplication.class, args);
    }

}
