package es.upm.miw.devops.service;

import es.upm.miw.devops.code.Fraction;
import es.upm.miw.devops.dto.UserDTO;
import es.upm.miw.devops.model.User;
import es.upm.miw.devops.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserDTOServiceTest {

    private final UserRepository userRepository = mock(UserRepository.class);
    private final UserService userService = new UserService(userRepository);

    @Test
    void testFindByIdReturnsUserFromDatabase() {
        User user = new User("1", "Oscar", "Fernandez", List.of(new Fraction(1, 1)));
        user.setEmail("oscar@example.com");
        user.setIdentity("12345678A");
        user.setAddress("Calle Mayor 1");
        user.setCity("Madrid");
        user.setProvince("Madrid");
        user.setPostalCode("28001");
        when(userRepository.findById("1")).thenReturn(java.util.Optional.of(user));

        UserDTO userDTO = userService.findById("1");

        assertThat(userDTO.getId()).isEqualTo("1");
        assertThat(userDTO.getName()).isEqualTo("Oscar");
        assertThat(userDTO.getFamilyName()).isEqualTo("Fernandez");
        assertThat(userDTO.isBillable()).isTrue();
    }

    @Test
    void testFindByIdThrowsNotFoundWhenUserDoesNotExist() {
        when(userRepository.findById("999")).thenReturn(java.util.Optional.empty());

        assertThatThrownBy(() -> userService.findById("999"))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("User not found: 999");
    }
}
