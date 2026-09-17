package es.upm.miw.devops.controller;

import es.upm.miw.devops.dto.UserDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
class UserDTOControllerFT {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testReadUserById() {
        webTestClient.get()
                .uri("/user/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserDTO.class)
                .value(user -> {
                    assertThat(user.getId()).isEqualTo("1");
                    assertThat(user.getName()).isEqualTo("Oscar");
                    assertThat(user.getFamilyName()).isEqualTo("Fernandez");
                });
    }

    @Test
    void testReadUserByIdNotFound() {
        webTestClient.get()
                .uri("/user/999")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.code").isEqualTo(404)
                .jsonPath("$.message").value(message -> assertThat(String.valueOf(message)).contains("User not found: 999"));
    }
}
