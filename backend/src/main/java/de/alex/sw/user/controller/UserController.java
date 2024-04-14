package de.alex.sw.user.controller;

import de.alex.sw.user.model.UserModel;
import de.alex.sw.user.service.UserService;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;

@Controller
@Path("/user")
public class UserController {
    private final UserService userService = UserService.getUserService();

    @GET
    public ArrayList<UserModel> getAllUsers() {
        ArrayList<UserModel> users = new ArrayList<>();
        users.add(new UserModel());
        return users;
    }

    @POST
    public UserModel createUser() {
        return new UserModel();
    }
}
