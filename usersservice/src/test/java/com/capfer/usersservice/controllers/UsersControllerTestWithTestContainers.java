package com.capfer.usersservice.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
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
    @ServiceConnection
    private static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:17.7");
            //.withDatabaseName("test_app") // uses the test database
//            .withUsername("postgres") // uses the test user
//            .withPassword("password"); // // uses the test password

    // Override the properties so our app can connect to postgres server that is running in docker container.
//    @DynamicPropertySource
//    public static void overrideProperties(DynamicPropertyRegistry propertyRegistry) {
//        propertyRegistry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
//        propertyRegistry.add("spring.datasource.username", postgreSQLContainer::getUsername);
//        propertyRegistry.add("spring.datasource.password", postgreSQLContainer::getPassword);
//    }

    @Test
    @DisplayName(value = "The PostgreSQL container is created and it's up and running...")
    void test_container_is_running() {
        Assertions.assertTrue(postgreSQLContainer.isCreated(), "PostgreSQL container was not created");
        Assertions.assertTrue(postgreSQLContainer.isRunning(), "PostgreSQL container is not running");
    }
}
