package es.upm.miw.devops.controller;

import es.upm.miw.devops.dto.UserDTO;
import es.upm.miw.devops.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
