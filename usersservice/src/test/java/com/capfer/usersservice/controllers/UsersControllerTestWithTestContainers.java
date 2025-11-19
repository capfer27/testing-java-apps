package com.capfer.usersservice.controllers;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

/**
 * SpringBootTest.WebEnvironment.RANDOM_PORT - to avoid port number conflicts when running tests in parallel.
 * Testcontainers - manages the lifecycle of services running inside docker containers. For, PostgreSQL docker container
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class UsersControllerTestWithTestContainers {

    // @Container - ensure that the postgresql container is created and started automatically before any test method
    // within this class is executed.
    @Container
    private static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:17.7")
            .withDatabaseName("test_app")
            .withUsername("postgres")
            .withPassword("");

    // Override the properties defined in the application.properties file.
    @DynamicPropertySource
    public static void overrideProperties(DynamicPropertyRegistry propertyRegistry) {
        propertyRegistry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        propertyRegistry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        propertyRegistry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }
}
