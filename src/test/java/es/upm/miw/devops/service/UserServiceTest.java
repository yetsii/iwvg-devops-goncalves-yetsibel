package es.upm.miw.devops.service;

import es.upm.miw.devops.code.User;
import es.upm.miw.devops.code.UsersDatabase;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserServiceTest {

    private final UserService userService = new UserService(new UsersDatabase());

    @Test
    void testFindByIdReturnsUserFromSeeder() {
        User user = userService.findById("1");

        assertThat(user.getId()).isEqualTo("1");
        assertThat(user.getName()).isEqualTo("Oscar");
        assertThat(user.getFamilyName()).isEqualTo("Fernandez");
    }

    @Test
    void testFindByIdThrowsNotFoundWhenUserDoesNotExist() {
        assertThatThrownBy(() -> userService.findById("999"))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("User not found: 999");
    }
}
