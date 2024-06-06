package de.alex.blogster_rest_api.user.controller;

import de.alex.blogster_rest_api.user.model.http.CreateUserRequest;
import de.alex.blogster_rest_api.user.model.User;
import de.alex.blogster_rest_api.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping(path = "/admin/user/")
public class UserAdminController {
    private final UserService userService;

    public UserAdminController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<User> createUser(@RequestBody CreateUserRequest user) {
//  TODO: fix with response entity type
//        if (userService.findUserByUsername(user.getUsername()) != null)
//            return ResponseEntityBuilder.buildErrorResponse("Username already exists");
//        if (userService.findUserByMailAddress(user.getMailAddress()) != null)
//            return ResponseEntityBuilder.buildErrorResponse("E-Mail address already used");

        User newUser = new User(
                user.getUsername(),
                user.getPassword(),
                user.getFullName(),
                user.getMailAddress()
        );
        newUser.setRole(user.getRole());
        return new ResponseEntity<>(userService.createUser(newUser), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<User> deleteUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.deleteUser(user), HttpStatus.OK);
    }

    @GetMapping(path = "/all/", produces = "application/json")
    public ResponseEntity<ArrayList<User>> getAllUsers() {
        return new ResponseEntity<>(userService.findAllUsers(), HttpStatus.OK);
    }
}
