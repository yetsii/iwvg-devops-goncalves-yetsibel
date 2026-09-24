package es.upm.miw.devops.service;

import es.upm.miw.devops.code.Fraction;
import es.upm.miw.devops.dto.UserActivePatchRequestDTO;
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

class UserServiceTest {

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

    @Test
    void testUpdateActiveBulkUpdatesOnlyActiveFlag() {
        User firstUser = new User("1", "Oscar", "Fernandez", List.of(new Fraction(1, 1)));
        firstUser.setEmail("oscar@example.com");
        firstUser.setIdentity("12345678A");
        firstUser.setAddress("Calle Mayor 1");
        firstUser.setCity("Madrid");
        firstUser.setProvince("Madrid");
        firstUser.setPostalCode("28001");
        firstUser.setActive(true);

        User secondUser = new User("2", "Ana", "Pérez", List.of(new Fraction(2, 1)));
        secondUser.setEmail("ana@example.com");
        secondUser.setIdentity("98765432B");
        secondUser.setAddress("Avenida Central 2");
        secondUser.setCity("Barcelona");
        secondUser.setProvince("Barcelona");
        secondUser.setPostalCode("08001");
        secondUser.setActive(false);

        when(userRepository.findById("1")).thenReturn(java.util.Optional.of(firstUser));
        when(userRepository.findById("2")).thenReturn(java.util.Optional.of(secondUser));
        when(userRepository.save(org.mockito.ArgumentMatchers.any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        List<UserDTO> result = userService.updateActive(List.of(
                new UserActivePatchRequestDTO("1", false),
                new UserActivePatchRequestDTO("2", true)
        ));

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getId()).isEqualTo("1");
        assertThat(result.get(0).isActive()).isFalse();
        assertThat(result.get(0).getName()).isEqualTo("Oscar");
        assertThat(result.get(1).getId()).isEqualTo("2");
        assertThat(result.get(1).isActive()).isTrue();
        assertThat(result.get(1).getName()).isEqualTo("Ana");
    }

    @Test
    void testUpdateActiveRejectsAdminUsers() {
        User adminUser = new User("99", "Admin", "User", List.of(new Fraction(1, 1)));
        adminUser.setEmail("admin@example.com");
        adminUser.setIdentity("11111111Z");
        adminUser.setAddress("Calle Admin 1");
        adminUser.setCity("Madrid");
        adminUser.setProvince("Madrid");
        adminUser.setPostalCode("28001");
        adminUser.setActive(true);
        adminUser.setAdmin(true);

        when(userRepository.findById("99")).thenReturn(java.util.Optional.of(adminUser));

        assertThatThrownBy(() -> userService.updateActive(List.of(new UserActivePatchRequestDTO("99", false))))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Admin users cannot change active status");
    }
}
