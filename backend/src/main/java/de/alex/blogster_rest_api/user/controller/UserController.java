package de.alex.blogster_rest_api.user.controller;

import de.alex.blogster_rest_api.security.authentication.UserPrincipal;
import de.alex.blogster_rest_api.security.encoder.PwdEncoder;
import de.alex.blogster_rest_api.user.model.UpdatePasswordRequest;
import de.alex.blogster_rest_api.user.model.UpdateUserInfoRequest;
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

    @PutMapping(path = "/", consumes = "application/json")
    public ResponseEntity<String> updateUser(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody UpdateUserInfoRequest userInfoRequest) {
        User user = userService.findUserById(userPrincipal.getId());
        userService.updateUser(user, userInfoRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(path = "/password", consumes = "application/json")
    public ResponseEntity<String> updatePassword(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody UpdatePasswordRequest passwordRequest) {
        User user = userService.findUserById(userPrincipal.getId());
        if (!PwdEncoder.getEncoder().matches(passwordRequest.getOldPassword(), user.getPassword()))
            return new ResponseEntity<>("Old password isn't valid", HttpStatus.CONFLICT);
        else {
            userService.updatePassword(user, passwordRequest.getNewPassword());
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<User> getUser(@PathVariable(name = "id") long id) {
        return new ResponseEntity<>(userService.findUserById(id), HttpStatus.OK);
    }
}
