package com.amuryllis.mephisto_hub_backend.contact;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.client.RestTestClient;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureRestTestClient
class ContactControllerIntegrationTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:17-alpine");

    @Autowired
    private RestTestClient restTestClient;

    @Autowired
    private ContactMessageRepository repository;

    @Test
    void submittingContactMessage_persistsItAndReturns201() {
        var payload = """
            {
                "name": "Integration Test",
                "email": "integration@test.com",
                "message": "Testing the full flow against a real Postgres container"
            }
            """;

        restTestClient.post()
                .uri("/api/contact")
                .contentType(MediaType.APPLICATION_JSON)
                .body(payload)
                .exchange()
                .expectStatus().isCreated();

        assertThat(repository.findAll())
                .anyMatch(m -> m.getEmail().equals("integration@test.com"));
    }

    @Test
    void listingMessages_withoutAuth_returns401() {
        restTestClient.get()
                .uri("/api/contact")
                .exchange()
                .expectStatus().isUnauthorized();
    }
}