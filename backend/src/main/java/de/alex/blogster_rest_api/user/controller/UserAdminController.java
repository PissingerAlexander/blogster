package de.alex.blogster_rest_api.user.controller;

import de.alex.blogster_rest_api.blog.service.BlogService;
import de.alex.blogster_rest_api.http.model.response.GetPage;
import de.alex.blogster_rest_api.user.model.http.create_user.CreateUserRequest;
import de.alex.blogster_rest_api.user.model.User;
import de.alex.blogster_rest_api.user.model.http.delete_users.DeleteUserResponse;
import de.alex.blogster_rest_api.user.model.http.get_page.GetUserPageResponse;
import de.alex.blogster_rest_api.user.model.http.get_user.GetUserResponse;
import de.alex.blogster_rest_api.user.service.UserService;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

    @DeleteMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<DeleteUserResponse> deleteUser(@PathVariable long id) {
        return new ResponseEntity<>(new DeleteUserResponse(userService.deleteUser(id)), HttpStatus.OK);
    }

    @GetMapping(path = "/all/", produces = "application/json")
    public ResponseEntity<ArrayList<User>> getAllUsers() {
        return new ResponseEntity<>(userService.findAllUsers(), HttpStatus.OK);
    }

    @GetMapping(path = "/all", produces = "application/json")
    public ResponseEntity<GetUserPageResponse> getUserPage(@RequestParam @NotNull int page, @RequestParam @NotNull int size) {
        Page<User> pages = userService.findUsersPage(page, size);
        int pageCount = pages.getTotalPages();
        List<User> users = pages.getContent();
        return new ResponseEntity<>(new GetUserPageResponse(new GetPage<>(pageCount, users)), HttpStatus.OK);
    }
}
