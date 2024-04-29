package de.alex.blogster_rest_api.user.controller;

import de.alex.blogster_rest_api.user.model.User;
import de.alex.blogster_rest_api.user.repository.UserRepository;
import de.alex.blogster_rest_api.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;


@RestController
@RequestMapping(path = "/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping(path = "/all", produces = "application/json")
    public ArrayList<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(produces = "application/json")
    public User getUser(@RequestParam("username") String username) {
        return userService.getUserByUsername(username);
    }

    @PostMapping(consumes = "application/json", produces = "text/plain")
    public boolean createUser(@RequestBody User user) {
        User newUser = new User(user.getUsername());
        newUser.setPassword(user.getPassword());
        newUser.setFullName(user.getFullName());
        newUser.setMailAddress(user.getMailAddress());

        return userService.createUser(newUser);
    }

    @GetMapping(path = "/test",produces = "text/html")
    public String test() {
        return "<h1>Hello World!</h1>";
    }
}
