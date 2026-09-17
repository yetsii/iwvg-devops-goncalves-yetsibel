package es.upm.miw.devops.code;

import es.upm.miw.devops.dto.UserDTO;

import java.util.List;
import java.util.stream.Stream;

public class UsersDatabase {

    public Stream<UserDTO> findAll() {

        List<Fraction> fractions1 = List.of(
                new Fraction(0, 1),
                new Fraction(1, 1),
                new Fraction(2, 1)
        );
        List<Fraction> fractions2 = List.of(
                new Fraction(2, 1),
                new Fraction(-1, 5),
                new Fraction(2, 4),
                new Fraction(4, 3)
        );
        List<Fraction> fractions3 = List.of(
                new Fraction(1, 5),
                new Fraction(3, -6),
                new Fraction(1, 2),
                new Fraction(4, 4)
        );
        List<Fraction> fractions4 = List.of(
                new Fraction(2, 2),
                new Fraction(4, 4)
        );
        List<Fraction> fractions5 = List.of(
                new Fraction(0, 1),
                new Fraction(0, -2),
                new Fraction(0, 3)
        );

        List<Fraction> fractions6 = List.of(
                new Fraction(0, 0),
                new Fraction(1, 0),
                new Fraction(1, 1)
        );

        return Stream.of(
                new UserDTO("1", "Oscar", "Fernandez", fractions1),
                new UserDTO("2", "Ana", "Blanco", fractions2),
                new UserDTO("3", "Oscar", "López", fractions3),
                new UserDTO("4", "Paula", "Torres", fractions4),
                new UserDTO("5", "Antonio", "Blanco", fractions5),
                new UserDTO("6", "Paula", "Torres", fractions6)
        );
    }
}
