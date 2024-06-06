package de.alex.blogster_rest_api.user.controller;

import de.alex.blogster_rest_api.role.model.Role;
import de.alex.blogster_rest_api.security.authentication.UserPrincipal;
import de.alex.blogster_rest_api.user.model.User;
import de.alex.blogster_rest_api.user.service.UserService;
import de.alex.blogster_rest_api.util.ResponseEntityBuilder;
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

    @PostMapping(path = "/", consumes = "application/json", produces = "text/plain")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        User newUser = new User(
                user.getUsername(),
                user.getPassword(),
                user.getFullName(),
                user.getMailAddress()
        );
        userService.createUser(newUser);
        return ResponseEntityBuilder.buildStringResponse("User successfully created");
    }

    @DeleteMapping(path = "/", consumes = "application/json", produces = "text/plain")
    public ResponseEntity<String> deleteUser(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody User user) {
        this.userService.deleteUser(user);
        return ResponseEntityBuilder.buildStringResponse("User successfully deleted");
    }

    @GetMapping(path = "/all", produces = "application/json")
    public ResponseEntity<ArrayList<User>> getAllUsers() {
        return new ResponseEntity<>(userService.findAllUsers(), HttpStatus.OK);
    }

    @PostMapping(path = "/admin", consumes = "application/json", produces = "text/plain")
    public ResponseEntity<String> createAdminUser(@RequestBody User user) {
        User newUser = new User(
                user.getUsername(),
                user.getPassword(),
                user.getFullName(),
                user.getMailAddress()
        );
        newUser.setRole(Role.ROLE_ADMIN);
        userService.createUser(newUser);
        return ResponseEntityBuilder.buildStringResponse("Admin successfully created");
    }
}
