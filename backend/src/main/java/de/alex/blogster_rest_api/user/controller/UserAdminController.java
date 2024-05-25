package de.alex.blogster_rest_api.user.controller;

import de.alex.blogster_rest_api.role.model.Role;
import de.alex.blogster_rest_api.user.model.User;
import de.alex.blogster_rest_api.user.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping(path = "/admin/user")
public class UserAdminController {
    private final UserService userService;

    public UserAdminController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "/", consumes = "application/json", produces = "text/plain")
    public boolean createUser(@RequestBody User user) {
        User newUser = new User(
                user.getUsername(),
                user.getPassword(),
                user.getFullName(),
                user.getMailAddress()
        );
        return userService.createUser(newUser);
    }

    @GetMapping(path = "/all", produces = "application/json")
    public ArrayList<User> getAllUsers() {
        return userService.findAllUsers();
    }

    @PostMapping(path = "/admin", consumes = "application/json", produces = "text/plain")
    public boolean createAdminUser(@RequestBody User user) {
        User newUser = new User(
                user.getUsername(),
                user.getPassword(),
                user.getFullName(),
                user.getMailAddress()
        );
        newUser.setRole(Role.ROLE_ADMIN);
        return userService.createUser(newUser);
    }
}
