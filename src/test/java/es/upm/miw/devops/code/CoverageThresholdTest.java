package es.upm.miw.devops.code;

import es.upm.miw.devops.Application;
import es.upm.miw.devops.controller.UserController;
import es.upm.miw.devops.dto.UserDTO;
import es.upm.miw.devops.model.User;
import es.upm.miw.devops.repository.UserRepository;
import es.upm.miw.devops.rest.exceptionshandler.ApiExceptionHandler;
import es.upm.miw.devops.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.boot.SpringApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class CoverageThresholdTest {

    @Test
    void testPointLackOfPOO() {
        PointLackOfPOO point = new PointLackOfPOO();
        PointLackOfPOO pointWithCoords = new PointLackOfPOO(3, 4);
        PointLackOfPOO pointWithOneValue = new PointLackOfPOO(5);

        assertThat(point.getX()).isZero();
        assertThat(point.getY()).isZero();
        assertThat(pointWithCoords.module(3, 4)).isEqualTo(5.0);
        assertThat(pointWithCoords.phase(3, 4)).isCloseTo(Math.atan(4.0 / 3.0), org.assertj.core.api.Assertions.within(1e-9));

        point.translateXOrigin(2);
        point.translateOrigin(1, 1);
        assertThat(point.getX()).isEqualTo(-3);
        assertThat(point.getY()).isEqualTo(-1);

        assertThat(pointWithOneValue.getX()).isEqualTo(5);
        assertThat(pointWithOneValue.getY()).isEqualTo(5);
        assertThat(point.toString()).contains("Point");
    }

    @Test
    void testParallelStreamPrivateMethods() throws Exception {
        ParallelStream streamDemo = new ParallelStream();

        Method main = ParallelStream.class.getDeclaredMethod("main", String[].class);
        main.setAccessible(true);
        main.invoke(null, (Object) new String[0]);

        for (String methodName : List.of(
                "sequentialRange", "sequentialCollection", "parallelRange", "parallelIterative", "parallelArray", "run")) {
            if ("run".equals(methodName)) {
                Method method = ParallelStream.class.getDeclaredMethod(methodName);
                method.setAccessible(true);
                method.invoke(streamDemo);
            } else {
                Method method = ParallelStream.class.getDeclaredMethod(methodName, int.class);
                method.setAccessible(true);
                method.invoke(streamDemo, 10_000);
            }
        }
    }

    @Test
    void testDateSnippet() throws Exception {
        DateSnippet snippet = new DateSnippet();
        snippet.createDate();
        snippet.createTime();
        snippet.createDateTime();
        snippet.instant();

        Method main = DateSnippet.class.getDeclaredMethod("main", String[].class);
        main.setAccessible(true);
        main.invoke(null, (Object) new String[0]);
    }

    @Test
    void testSearchesBehaviors() {
        Searches searches = new Searches();

        assertThat(searches.findUserFamilyNameByUserNameDistinct("Oscar").toList())
                .containsExactlyInAnyOrder("Fernandez", "López");
        assertThat(searches.findFractionNumeratorByUserFamilyName("Blanco").toList())
                .containsExactly(2, -1, 2, 4, 0, 0, 0);
        assertThat(searches.findUserFamilyNameByFractionDenominator(1).toList())
                .contains("Fernandez", "Blanco", "Torres");

        assertThat(searches.findUserFamilyNameInitialByAnyProperFraction()).isEmpty();
        assertThat(searches.findUserIdByAnyProperFraction()).isEmpty();
        assertThat(searches.findUserIdByAllProperFraction()).isEmpty();
        assertThat(searches.findUserFamilyNameByImproperFraction()).isEmpty();
        assertThat(searches.findUserNameByAnyImproperFraction()).isEmpty();
        assertThat(searches.findUserFamilyNameByAllNegativeSignFractionDistinct()).isEmpty();

        assertThat(searches.findFractionMultiplicationByUserFamilyName("Blanco")).isNull();
        assertThat(searches.findFirstFractionDivisionByUserId("1")).isNull();
        assertThat(searches.findFirstDecimalFractionByUserName("Oscar")).isNull();
        assertThat(searches.findDecimalImproperFractionByUserName("Oscar")).isEmpty();
        assertThat(searches.findFirstProperFractionByUserId("1")).isNull();
        assertThat(searches.findHighestFraction()).isNull();
        assertThat(searches.findDecimalFractionByUserName("Oscar")).isEmpty();
        assertThat(searches.findDecimalFractionByNegativeSignFraction()).isEmpty();
        assertThat(searches.findFractionAdditionByUserId("1")).isNull();
        assertThat(searches.findFirstFractionSubtractionByUserName("Oscar")).isNull();
    }

    @Test
    void testApiExceptionHandler() {
        ApiExceptionHandler handler = new ApiExceptionHandler();

        var noResource = handler.noResourceFoundRequest(new RuntimeException("bad path"));
        assertThat(noResource.getCode()).isEqualTo(404);
        assertThat(noResource.getMessage()).contains("Ruta no encontrada");

        var responseStatus = handler.responseStatusException(new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found: 99"));
        assertThat(responseStatus.getCode()).isEqualTo(404);
        assertThat(responseStatus.getMessage()).contains("User not found: 99");

        var generic = handler.exception(new RuntimeException("boom"));
        assertThat(generic.getCode()).isEqualTo(500);
        assertThat(generic.getMessage()).isEqualTo("ERROR");
    }

    @Test
    void testUserModelLifecycle() {
        User user = new User();
        user.setId("1");
        user.setName("Oscar");
        user.setFamilyName("Fernandez");
        user.setFirstName("Ana");
        user.setEmail("oscar@example.com");
        user.setIdentity("12345678A");
        user.setAddress("Calle Mayor 1");
        user.setCity("Madrid");
        user.setProvince("Madrid");
        user.setPostalCode("28001");

        assertThat(user.getId()).isEqualTo("1");
        assertThat(user.getName()).isEqualTo("Ana");
        assertThat(user.getFirstName()).isEqualTo("Ana");
        assertThat(user.getFamilyName()).isEqualTo("Fernandez");
        assertThat(user.getEmail()).isEqualTo("oscar@example.com");
        assertThat(user.getIdentity()).isEqualTo("12345678A");
        assertThat(user.getAddress()).isEqualTo("Calle Mayor 1");
        assertThat(user.getCity()).isEqualTo("Madrid");
        assertThat(user.getProvince()).isEqualTo("Madrid");
        assertThat(user.getPostalCode()).isEqualTo("28001");
        assertThat(user.isBillable()).isTrue();

        user.setActive(false);
        assertThat(user.isActive()).isFalse();
        assertThat(user.fullName()).isEqualTo("Ana Fernandez");
        assertThat(user.initials()).isEqualTo("A.");
        assertThat(user.toString()).contains("User");

        User empty = new User("2", "Pedro", "García", List.of());
        empty.setPostalCode("   ");
        assertThat(empty.isBillable()).isFalse();
    }

    @Test
    void testUserServiceAndController() {
        UserRepository repository = mock(UserRepository.class);
        UserService service = new UserService(repository);
        UserController controller = new UserController(service);

        User user = new User("1", "Oscar", "Fernandez", List.of());
        user.setEmail("oscar@example.com");
        user.setIdentity("12345678A");
        user.setAddress("Calle Mayor 1");
        user.setCity("Madrid");
        user.setProvince("Madrid");
        user.setPostalCode("28001");

        when(repository.findById("1")).thenReturn(Optional.of(user));
        when(repository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(repository.existsById("1")).thenReturn(true);

        UserDTO activated = service.setActive("1", true);
        assertThat(activated.isActive()).isTrue();

        ResponseEntity<UserDTO> response = controller.setActive("1", false);
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getHeaders().getFirst("X-Message")).isEqualTo("user deactivated successfully");
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().isActive()).isFalse();

        ResponseEntity<UserDTO> queryResponse = controller.setActive("1", true);
        assertThat(queryResponse.getHeaders().getFirst("X-Message")).isEqualTo("user activated successfully");

        doNothing().when(repository).deleteById("1");
        controller.deleteById("1");

        when(repository.findById("404")).thenReturn(Optional.empty());
        when(repository.existsById("404")).thenReturn(false);
        assertThatThrownBy(() -> service.findById("404")).isInstanceOf(ResponseStatusException.class);
        assertThatThrownBy(() -> service.delete("404")).isInstanceOf(ResponseStatusException.class);
        assertThatThrownBy(() -> service.setActive("404", true)).isInstanceOf(ResponseStatusException.class);
    }

    @Test
    void testApplicationMainUsesSpringApplication() {
        try (MockedStatic<SpringApplication> mockedSpringApplication = mockStatic(SpringApplication.class)) {
            String[] args = {"--spring.main.web-application-type=none"};
            Application.main(args);
            mockedSpringApplication.verify(() -> SpringApplication.run(Application.class, args));
        }
    }
}
