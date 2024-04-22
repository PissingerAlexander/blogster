package de.alex.sw.user.controller;

import de.alex.sw.user.model.User;
import de.alex.sw.user.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController(value = "/user")
public class UserController {
    private final UserService userService = UserService.getUserService();

    @GetMapping
    public ArrayList<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(value = "/test", produces = "text/html")
    public String test() {
        return "<html><body><h1>test</h1></body></html>";
    }

    @PostMapping
    public boolean createUser() {
        System.out.println();
        return userService.createUser(new User());
    }
}
