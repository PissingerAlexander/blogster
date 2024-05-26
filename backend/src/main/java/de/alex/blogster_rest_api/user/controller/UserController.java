package de.alex.blogster_rest_api.user.controller;

import de.alex.blogster_rest_api.security.authentication.UserPrincipal;
import de.alex.blogster_rest_api.user.model.User;
import de.alex.blogster_rest_api.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/", produces = "application/json")
    public ResponseEntity<User> getCurrentUser(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return new ResponseEntity<>(userService.findUserById(userPrincipal.getId()), HttpStatus.OK);
    }

    @RequestMapping(path = "/", method = RequestMethod.PUT, consumes = "application/json")
    public ResponseEntity<Void> updateUser(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody User user) {
        User oldUser = userService.findUserById(userPrincipal.getId());
        userService.updateUser(userPrincipal.getId(), oldUser, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/{username}", produces = "application/json")
    public ResponseEntity<User> getUser(@PathVariable(name = "username") String username) {
        return new ResponseEntity<>(userService.findUserByUsername(username), HttpStatus.OK);
    }
}
