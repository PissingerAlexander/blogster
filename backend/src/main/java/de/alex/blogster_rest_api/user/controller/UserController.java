package de.alex.blogster_rest_api.user.controller;

import de.alex.blogster_rest_api.user.model.User;
import de.alex.blogster_rest_api.user.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;


@RestController
@RequestMapping(path = "/user")
public class UserController {
    private final UserService userService = UserService.getUserService();

    @GetMapping(produces = "application/json")
    public ArrayList<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public boolean createUser() {
        System.out.println();
        return userService.createUser(new User());
    }

    @GetMapping(path = "/test",produces = "text/html")
    public String test() {
        return "<h1>Hello World!</h1>";
    }
}
