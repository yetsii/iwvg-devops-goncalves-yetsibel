package es.upm.miw.devops.controller;

import es.upm.miw.devops.code.Fraction;
import es.upm.miw.devops.dto.UserActivePatchRequestDTO;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
class UserControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private UserService userService;

    @Test
    void testReadUserById() {
        UserDTO userDTO = new UserDTO("1", "Oscar", "Fernandez", true, List.of(new Fraction(1,1)));
        when(userService.findById("1")).thenReturn(userDTO);

        webTestClient.get()
                .uri("/users/1")
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
                .uri("/users/999")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.code").isEqualTo(404)
                .jsonPath("$.message").value(message -> assertThat(String.valueOf(message)).contains("User not found: 999"));
    }

    @Test
    void testDeleteUserById() {
        org.mockito.Mockito.doNothing().when(userService).delete("1");

        webTestClient.delete()
                .uri("/users/1")
                .exchange()
                .expectStatus().isNoContent()
                .expectHeader().valueEquals("X-Message", "user deleted successfully");
    }

    @Test
    void testDeleteUserByIdNotFound() {
        org.mockito.Mockito.doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found: 999"))
                .when(userService).delete("999");

        webTestClient.delete()
                .uri("/users/999")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.code").isEqualTo(404)
                .jsonPath("$.message").value(message -> assertThat(String.valueOf(message)).contains("User not found: 999"));
    }

    @Test
    void testUpdateUserById() {
        UserDTO request = new UserDTO("1", "Oscar", "García", true, false, List.of(new Fraction(1, 1)));
        UserDTO response = new UserDTO("1", "Oscar", "García", true, false, List.of(new Fraction(1, 1)));
        when(userService.update(eq("1"), any(UserDTO.class))).thenReturn(response);

        webTestClient.put()
                .uri("/users/1")
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("X-Message", "user updated successfully")
                .expectBody(UserDTO.class)
                .value(user -> {
                    assertThat(user.getId()).isEqualTo("1");
                    assertThat(user.getFamilyName()).isEqualTo("García");
                    assertThat(user.isActive()).isFalse();
                });
    }

    @Test
    void testUpdateUserByIdNotFound() {
        UserDTO request = new UserDTO("999", "Ana", "Pérez", true, List.of(new Fraction(1, 1)));
        when(userService.update(eq("999"), any(UserDTO.class))).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found: 999"));

        webTestClient.put()
                .uri("/users/999")
                .bodyValue(request)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.code").isEqualTo(404)
                .jsonPath("$.message").value(message -> assertThat(String.valueOf(message)).contains("User not found: 999"));
    }

    @Test
    void testUpdateUserByIdCannotChangeId() {
        UserDTO request = new UserDTO("999", "Ana", "Pérez", true, true, List.of(new Fraction(1, 1)));
        when(userService.update(eq("1"), any(UserDTO.class)))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "User id cannot be modified"));

        webTestClient.put()
                .uri("/users/1")
                .bodyValue(request)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.code").isEqualTo(404)
                .jsonPath("$.message").value(message -> assertThat(String.valueOf(message)).contains("User id cannot be modified"));
    }

    @Test
    void testSetUserActive() {
        UserDTO userDTO = new UserDTO("1", "Oscar", "Fernandez", true, true, List.of(new Fraction(1,1)));
        when(userService.setActive("1", true)).thenReturn(userDTO);

        webTestClient.put()
                .uri("/users/1/active?active=true")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("X-Message", "user activated successfully")
                .expectBody(UserDTO.class)
                .value(user -> {
                    assertThat(user.getId()).isEqualTo("1");
                    assertThat(user.isActive()).isTrue();
                });
    }

    @Test
    void testSetUserActiveNotFound() {
        when(userService.setActive("999", true)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found: 999"));

        webTestClient.put()
                .uri("/users/999/active?active=true")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.code").isEqualTo(404)
                .jsonPath("$.message").value(message -> assertThat(String.valueOf(message)).contains("User not found: 999"));
    }

    @Test
    void testPatchUsersActive() {
        List<UserActivePatchRequestDTO> request = List.of(
                new UserActivePatchRequestDTO("1", false),
                new UserActivePatchRequestDTO("2", true)
        );
        List<UserDTO> response = List.of(
                new UserDTO("1", "Oscar", "Fernandez", true, false, List.of(new Fraction(1, 1))),
                new UserDTO("2", "Ana", "Pérez", true, true, List.of(new Fraction(2, 1)))
        );
        when(userService.updateActive(any())).thenReturn(response);

        webTestClient.patch()
                .uri("/users")
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("X-Message", "users updated successfully")
                .expectBodyList(UserDTO.class)
                .hasSize(2)
                .value(users -> {
                    assertThat(users.get(0).getId()).isEqualTo("1");
                    assertThat(users.get(0).isActive()).isFalse();
                    assertThat(users.get(1).getId()).isEqualTo("2");
                    assertThat(users.get(1).isActive()).isTrue();
                });
    }

    @Test
    void testPatchUsersActiveNotFound() {
        when(userService.updateActive(any())).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found: 999"));

        webTestClient.patch()
                .uri("/users")
                .bodyValue(List.of(new UserActivePatchRequestDTO("999", true)))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.code").isEqualTo(404)
                .jsonPath("$.message").value(message -> assertThat(String.valueOf(message)).contains("User not found: 999"));
    }
}
