package de.alex.blogster_rest_api.user.controller;

import de.alex.blogster_rest_api.security.authentication.UserPrincipal;
import de.alex.blogster_rest_api.security.encoder.PwdEncoder;
import de.alex.blogster_rest_api.user.model.http.UpdatePasswordRequest;
import de.alex.blogster_rest_api.user.model.http.UpdateUserInfoRequest;
import de.alex.blogster_rest_api.user.model.User;
import de.alex.blogster_rest_api.user.service.UserService;
import de.alex.blogster_rest_api.util.ResponseEntityBuilder;
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

    @PutMapping(path = "/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<User> updateUser(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody UpdateUserInfoRequest userInfoRequest) {
        User user = userService.findUserById(userPrincipal.getId());
        return new ResponseEntity<>(userService.updateUser(user, userInfoRequest), HttpStatus.OK);
    }

    @PutMapping(path = "/password/", consumes = "application/json")
    public ResponseEntity<String> updatePassword(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody UpdatePasswordRequest passwordRequest) {
        User user = userService.findUserById(userPrincipal.getId());
        if (!PwdEncoder.getEncoder().matches(passwordRequest.getOldPassword(), user.getPassword()))
            return ResponseEntityBuilder.buildErrorResponse("Old password isn't valid");
        else {
            userService.updatePassword(user, passwordRequest.getNewPassword());
            return ResponseEntityBuilder.buildStringResponse("Password updated successfully");
        }
    }

    @GetMapping(path = "/{id}/", produces = "application/json")
    public ResponseEntity<User> getUser(@PathVariable(name = "id") long id) {
        return new ResponseEntity<>(userService.findUserById(id), HttpStatus.OK);
    }
}
