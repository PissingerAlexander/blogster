package de.alex.blogster_rest_api.user.controller;

import de.alex.blogster_rest_api.user.model.http.create_user.CreateUserRequest;
import de.alex.blogster_rest_api.user.model.User;
import de.alex.blogster_rest_api.user.model.http.get_user.GetUserResponse;
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
    public ResponseEntity<GetUserResponse> createUser(@RequestBody CreateUserRequest user) {
        if (userService.findUserByUsername(user.getUsername()) != null)
            return new ResponseEntity<>(new GetUserResponse("Username already exists"), HttpStatus.CONFLICT);
        if (userService.findUserByMailAddress(user.getMailAddress()) != null)
            return new ResponseEntity<>(new GetUserResponse("E-Mail address already used"), HttpStatus.CONFLICT);

        User newUser = new User(
                user.getUsername(),
                user.getPassword(),
                user.getFullName(),
                user.getMailAddress()
        );
        newUser.setRole(user.getRole());
        return new ResponseEntity<>(new GetUserResponse(userService.createUser(newUser)), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<GetUserResponse> deleteUser(@RequestBody User user) {
        return new ResponseEntity<>(new GetUserResponse(userService.deleteUser(user)), HttpStatus.OK);
    }

    @GetMapping(path = "/all/", produces = "application/json")
    public ResponseEntity<ArrayList<User>> getAllUsers() {
        return new ResponseEntity<>(userService.findAllUsers(), HttpStatus.OK);
    }
}
