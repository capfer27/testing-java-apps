package com.capfer.usersservice.controllers;

import com.capfer.usersservice.service.UsersService;
import com.capfer.usersservice.ui.response.UserRest;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.*;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.Arrays;
import java.util.List;

/**
 * SpringBootTest.WebEnvironment.RANDOM_PORT - to avoid port number conflicts when running tests in parallel.
 * Testcontainers - manages the lifecycle of services running inside docker containers. For, PostgreSQL docker container
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class UsersControllerTestWithTestContainers {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private UsersService usersService;

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

    @Test
    @DisplayName("The User should be created successfully.")
    //@Order(1)
    void testCreateUser_whenValidDetailsProvided_returnsUserDetails() throws JSONException {

        JSONObject userDetailsRequestJson = new JSONObject();
        userDetailsRequestJson.put("firstName", "Carlos");
        userDetailsRequestJson.put("lastName", "Pereira");
        userDetailsRequestJson.put("email", "test@test.com");
        userDetailsRequestJson.put("password","12345678");
        userDetailsRequestJson.put("repeatPassword", "12345678");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<String> request = new HttpEntity<>(userDetailsRequestJson.toString(), headers);

        // Act - send post request
        ResponseEntity<UserRest> createdUserDetailsEntity = testRestTemplate.postForEntity("/api/users",
                request,
                UserRest.class);
        UserRest createdUserDetails = createdUserDetailsEntity.getBody();

        // Assert
        Assertions.assertEquals(HttpStatus.OK, createdUserDetailsEntity.getStatusCode());
        Assertions.assertEquals(userDetailsRequestJson.getString("firstName"),
                createdUserDetails.getFirstName(),
                "Returned user's first name seems to be incorrect");
        Assertions.assertEquals(userDetailsRequestJson.getString("lastName"),
                createdUserDetails.getLastName(),
                "Returned user's last name seems to be incorrect");
        Assertions.assertEquals(userDetailsRequestJson.getString("email"),
                createdUserDetails.getEmail(),
                "Returned user's email seems to be incorrect");
        Assertions.assertFalse(createdUserDetails.getUserId().trim().isEmpty(),
                "User id should not be empty");

        System.out.println("The Created Users: ");
        usersService.getUsers(1, 5)
                .forEach(System.out::println);
    }
}
