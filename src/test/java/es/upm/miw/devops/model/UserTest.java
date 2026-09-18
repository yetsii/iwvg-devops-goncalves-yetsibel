package es.upm.miw.devops.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    @Test
    void testIsBillableWhenAllRequiredFieldsHaveRealContent() {
        User user = new User("1", "Oscar", "Fernandez", List.of());
        user.setEmail("oscar@example.com");
        user.setIdentity("12345678A");
        user.setAddress("Calle Mayor 1");
        user.setCity("Madrid");
        user.setProvince("Madrid");
        user.setPostalCode("28001");

        assertThat(user.isBillable()).isTrue();
    }

    @Test
    void testIsNotBillableWhenAnyRequiredFieldIsBlank() {
        User user = new User("1", "Oscar", "Fernandez", List.of());
        user.setEmail("oscar@example.com");
        user.setIdentity("12345678A");
        user.setAddress("Calle Mayor 1");
        user.setCity("Madrid");
        user.setProvince("Madrid");
        user.setPostalCode("   ");

        assertThat(user.isBillable()).isFalse();
    }
}
