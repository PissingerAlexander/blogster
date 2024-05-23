package de.alex.blogster_rest_api.user.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import de.alex.blogster_rest_api.role.model.Role;
import de.alex.blogster_rest_api.security.authentication.UserPrincipal;
import de.alex.blogster_rest_api.security.service.JwtDecoderService;
import de.alex.blogster_rest_api.user.model.User;
import de.alex.blogster_rest_api.user.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.UUID;


@RestController
@RequestMapping(path = "/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService, JwtDecoderService jwtDecoderService) {
        this.userService = userService;
    }

    @GetMapping(path = "/all", produces = "application/json")
    public ArrayList<User> getAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping(path = "/", produces = "application/json")
    public User getCurrentUser(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return userService.findUserByUuid(userPrincipal.getUuid());
    }

    @GetMapping(path = "/{username}", produces = "application/json")
    public User getUser(@PathVariable(name = "username") String username) {
        return userService.findUserByUsername(username);
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
