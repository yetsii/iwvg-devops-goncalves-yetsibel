package es.upm.miw.devops.controller;

import es.upm.miw.devops.dto.UserActivePatchRequestDTO;
import es.upm.miw.devops.dto.UserDTO;
import es.upm.miw.devops.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping({"/users"})
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public UserDTO findById(@PathVariable String id) {
        return userService.findById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        userService.delete(id);
        return ResponseEntity.noContent().header("X-Message", "user deleted successfully").build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateById(@PathVariable String id, @RequestBody UserDTO userDTO) {
        UserDTO updatedUser = userService.update(id, userDTO);
        return ResponseEntity.ok()
                .header("X-Message", "user updated successfully")
                .body(updatedUser);
    }

    @PutMapping("/{id}/active")
    public ResponseEntity<UserDTO> setActive(
            @PathVariable String id,
            @RequestParam(value = "active", defaultValue = "true") boolean active) {
        UserDTO updatedUser = userService.setActive(id, active);
        return ResponseEntity.ok()
                .header("X-Message", active ? "user activated successfully" : "user deactivated successfully")
                .body(updatedUser);
    }

    @PatchMapping
    public ResponseEntity<List<UserDTO>> patchActive(@RequestBody List<UserActivePatchRequestDTO> userUpdates) {
        List<UserDTO> updatedUsers = userService.updateActive(userUpdates);
        return ResponseEntity.ok()
                .header("X-Message", "users updated successfully")
                .body(updatedUsers);
    }

}
