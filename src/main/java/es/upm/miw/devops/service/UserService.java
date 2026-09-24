package es.upm.miw.devops.service;

import es.upm.miw.devops.dto.UserActivePatchRequestDTO;
import es.upm.miw.devops.dto.UserDTO;
import es.upm.miw.devops.model.User;
import es.upm.miw.devops.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
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

    @Transactional
    public void delete(String id) {
        if (!userRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found: " + id);
        }
        userRepository.deleteById(id);
    }

    @Transactional
    public UserDTO setActive(String id, boolean active) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found: " + id));
        validateActiveStatusChange(user);
        user.setActive(active);
        return toDto(userRepository.save(user));
    }

    @Transactional
    public List<UserDTO> updateActive(List<UserActivePatchRequestDTO> userUpdates) {
        if (userUpdates == null || userUpdates.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Users payload is required");
        }

        List<UserDTO> updatedUsers = new ArrayList<>();
        for (UserActivePatchRequestDTO userUpdate : userUpdates) {
            if (userUpdate == null || userUpdate.getId() == null || userUpdate.getId().isBlank()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User id is required");
            }

            User user = userRepository.findById(userUpdate.getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found: " + userUpdate.getId()));

            validateActiveStatusChange(user);
            user.setActive(userUpdate.isActive());
            updatedUsers.add(toDto(userRepository.save(user)));
        }
        return updatedUsers;
    }

    @Transactional
    public UserDTO update(String id, UserDTO userDTO) {
        if (userDTO == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User payload is required");
        }
        if (userDTO.getId() != null && !id.equals(userDTO.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User id cannot be modified");
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found: " + id));

        if (user.isAdmin() && userDTO.isActive() != user.isActive()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Admin users cannot change active status");
        }

        if (userDTO.getName() != null) {
            user.setName(userDTO.getName());
        }
        if (userDTO.getFamilyName() != null) {
            user.setFamilyName(userDTO.getFamilyName());
        }
        if (userDTO.getFractions() != null) {
            user.setFractions(userDTO.getFractions());
        }
        user.setId(id);
        user.setActive(userDTO.isActive());

        return toDto(userRepository.save(user));
    }

    private static void validateActiveStatusChange(User user) {
        if (user.isAdmin()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Admin users cannot change active status");
        }
    }

    private static UserDTO toDto(User user) {
        UserDTO dto = new UserDTO(user.getId(), user.getName(), user.getFamilyName(), user.getFractions());
        dto.setBillable(user.isBillable());
        dto.setActive(user.isActive());
        dto.setAdmin(user.isAdmin());
        return dto;
    }
}
