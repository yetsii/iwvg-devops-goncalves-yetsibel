package es.upm.miw.devops.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

import static es.upm.miw.devops.controller.SystemController.SYSTEM;
import static es.upm.miw.devops.controller.SystemController.VERSION_BADGE;
import static org.assertj.core.api.Assertions.assertThat;

@WebFluxTest(SystemController.class)
@AutoConfigureWebTestClient
@TestPropertySource(properties = {
        "info.app.artifact=test-app",
        "info.app.version=1.0.0",
        "info.app.build=20260918",
        "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration"
})
class SystemControllerFT {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testReadBadge() {
        webTestClient.get()
                .uri(VERSION_BADGE)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(body -> assertThat(body)
                        .isNotNull()
                        .startsWith("<svg"));
    }

    @Test
    void testReadInfo() {
        webTestClient.get()
                .uri(SYSTEM)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(body -> assertThat(body)
                        .isNotNull()
                        .isNotEmpty());
    }
}
