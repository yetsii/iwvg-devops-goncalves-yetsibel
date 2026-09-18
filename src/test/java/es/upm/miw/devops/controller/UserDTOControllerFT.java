package es.upm.miw.devops.controller;

import es.upm.miw.devops.code.Fraction;
import es.upm.miw.devops.dto.UserDTO;
import es.upm.miw.devops.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
class UserDTOControllerFT {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private UserService userService;

    @Test
    void testReadUserById() {
        UserDTO userDTO = new UserDTO("1", "Oscar", "Fernandez", true, List.of(new Fraction(1,1)));
        when(userService.findById("1")).thenReturn(userDTO);

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
        when(userService.findById("999")).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found: 999"));

        webTestClient.get()
                .uri("/user/999")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.code").isEqualTo(404)
                .jsonPath("$.message").value(message -> assertThat(String.valueOf(message)).contains("User not found: 999"));
    }
}
