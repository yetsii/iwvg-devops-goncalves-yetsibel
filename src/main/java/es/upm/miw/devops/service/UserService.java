package es.upm.miw.devops.service;

import es.upm.miw.devops.code.User;
import es.upm.miw.devops.code.UsersDatabase;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@Service
public class UserService {

    private final UsersDatabase usersDatabase;

    public UserService() {
        this(new UsersDatabase());
    }

    public UserService(UsersDatabase usersDatabase) {
        this.usersDatabase = Objects.requireNonNull(usersDatabase, "usersDatabase cannot be null");
    }

    public User findById(String id) {
        return this.usersDatabase.findAll()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found: " + id));
    }
}
