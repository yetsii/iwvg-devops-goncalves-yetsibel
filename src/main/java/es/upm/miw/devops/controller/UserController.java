package es.upm.miw.devops.controller;

import es.upm.miw.devops.dto.UserDTO;
import es.upm.miw.devops.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/user")
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

    @PutMapping("/{id}/active")
    public ResponseEntity<UserDTO> setActive(
            @PathVariable String id,
            @RequestParam(value = "active", required = false) Boolean activeParam,
            @RequestBody(required = false) Object body) {
        boolean active = resolveActive(activeParam, body, true);
        UserDTO updatedUser = userService.setActive(id, active);
        return ResponseEntity.ok()
                .header("X-Message", active ? "user activated successfully" : "user deactivated successfully")
                .body(updatedUser);
    }

    private boolean resolveActive(Boolean activeParam, Object body, boolean defaultValue) {
        if (body != null) {
            if (body instanceof Boolean boolValue) {
                return boolValue;
            }
            if (body instanceof String stringValue) {
                return Boolean.parseBoolean(stringValue);
            }
            if (body instanceof Map<?, ?> mapValue) {
                Object activeValue = mapValue.get("active");
                if (activeValue == null) {
                    activeValue = mapValue.get("isActive");
                }
                if (activeValue instanceof Boolean boolValue) {
                    return boolValue;
                }
                if (activeValue instanceof String stringValue) {
                    return Boolean.parseBoolean(stringValue);
                }
            }
        }
        if (activeParam != null) {
            return activeParam;
        }
        return defaultValue;
    }
}
