package es.upm.miw.devops.service;

import es.upm.miw.devops.dto.UserDTO;
import es.upm.miw.devops.model.User;
import es.upm.miw.devops.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = Objects.requireNonNull(userRepository, "userRepository cannot be null");
    }

    @Transactional(readOnly = true)
    public UserDTO findById(String id) {
        return userRepository.findById(id)
                .map(UserService::toDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found: " + id));
    }

    private static UserDTO toDto(User user) {
        return new UserDTO(user.getId(), user.getName(), user.getFamilyName(), user.getFractions());
    }
}
