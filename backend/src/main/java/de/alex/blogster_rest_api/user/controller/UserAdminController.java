package de.alex.blogster_rest_api.user.controller;

import de.alex.blogster_rest_api.role.model.Role;
import de.alex.blogster_rest_api.security.authentication.UserPrincipal;
import de.alex.blogster_rest_api.user.model.User;
import de.alex.blogster_rest_api.user.service.UserService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping(path = "/admin/user")
public class UserAdminController {
    private final UserService userService;

    public UserAdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/", produces = "text/plain")
    public ResponseEntity<Boolean> isAdmin(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        System.out.println(userPrincipal.getAuthorities());
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PostMapping(path = "/", consumes = "application/json", produces = "text/plain")
    public ResponseEntity<Boolean> createUser(@RequestBody User user) {
        User newUser = new User(
                user.getUsername(),
                user.getPassword(),
                user.getFullName(),
                user.getMailAddress()
        );
        return new ResponseEntity<>(userService.createUser(newUser), HttpStatus.CREATED);
    }

    @GetMapping(path = "/all", produces = "application/json")
    public ResponseEntity<ArrayList<User>> getAllUsers() {
        return new ResponseEntity<>(userService.findAllUsers(), HttpStatus.OK);
    }

    @PostMapping(path = "/admin", consumes = "application/json", produces = "text/plain")
    public ResponseEntity<Boolean> createAdminUser(@RequestBody User user) {
        User newUser = new User(
                user.getUsername(),
                user.getPassword(),
                user.getFullName(),
                user.getMailAddress()
        );
        newUser.setRole(Role.ROLE_ADMIN);
        return new ResponseEntity<>(userService.createUser(newUser), HttpStatus.CREATED);
    }
}
