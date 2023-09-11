package com.file.storage.service;

import com.file.storage.dto.UserRegistrationRequest;
import com.file.storage.model.User;
import com.file.storage.repository.UserRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("dev")
class UserServiceIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void beforeEach() {
        userRepository.deleteAll();
    }

    @Test
    void register_shouldSaveUserInDatabase() {
        var userRegistrationRequest = new UserRegistrationRequest();
        userRegistrationRequest.setEmail("user@gmail.com");
        userRegistrationRequest.setUsername("user");
        userRegistrationRequest.setPassword("password");

        userService.register(userRegistrationRequest);

        Optional<User> user = userRepository.findByUsername("user");
        assertTrue(user.isPresent(), "User should exist in the database");
        user.ifPresent(u -> {
            assertEquals("user@gmail.com", u.getEmail(), "Email should match");
            assertNotEquals("password", u.getPassword(), "Password should be hashed");
        });
    }

    @Test
    void register_sameUserTwice_shouldThrowException() {
        var userRegistrationRequest = new UserRegistrationRequest();
        userRegistrationRequest.setEmail("user@gmail.com");
        userRegistrationRequest.setUsername("user");
        userRegistrationRequest.setPassword("password");

        userService.register(userRegistrationRequest);

        assertThrows(
                DataIntegrityViolationException.class,
                () -> userService.register(userRegistrationRequest)
        );
        assertEquals(1, userRepository.findAll().size(), "User should be saved only once");
    }

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.datasource.driver-class-name", postgres::getDriverClassName);
    }
}