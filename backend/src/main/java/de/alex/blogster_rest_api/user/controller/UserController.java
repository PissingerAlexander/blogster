package de.alex.blogster_rest_api.user.controller;

import de.alex.blogster_rest_api.security.authentication.UserPrincipal;
import de.alex.blogster_rest_api.security.encoder.PwdEncoder;
import de.alex.blogster_rest_api.security.service.JwtIssuerService;
import de.alex.blogster_rest_api.user.model.http.UpdatePasswordRequest;
import de.alex.blogster_rest_api.user.model.http.UpdateUserInfoRequest;
import de.alex.blogster_rest_api.user.model.User;
import de.alex.blogster_rest_api.user.model.http.UpdateUserInfoResponse;
import de.alex.blogster_rest_api.user.model.http.UserResponse;
import de.alex.blogster_rest_api.user.model.http.response_type.UpdateUserInfoResponseType;
import de.alex.blogster_rest_api.user.service.UserService;
import de.alex.blogster_rest_api.util.ResponseEntityBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/user/")
public class UserController {
    private final UserService userService;
    private final JwtIssuerService jwtIssuerService;

    public UserController(UserService userService, JwtIssuerService jwtIssuerService) {
        this.userService = userService;
        this.jwtIssuerService = jwtIssuerService;
    }

    @GetMapping(path = "/", produces = "application/json")
    public ResponseEntity<UserResponse> getCurrentUser(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return new ResponseEntity<>(new UserResponse(userService.findUserById(userPrincipal.getId())), HttpStatus.OK);
    }

    @PutMapping(path = "/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<UpdateUserInfoResponse> updateUser(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody UpdateUserInfoRequest userInfoRequest) {
        if (userService.findUserByUsername(userInfoRequest.getUsername()) != null && userService.findUserByUsername(userInfoRequest.getUsername()).getId() != userPrincipal.getId())
            return new ResponseEntity<>(new UpdateUserInfoResponse("Username already in use"), HttpStatus.CONFLICT);
        if (userService.findUserByMailAddress(userInfoRequest.getMailAddress()) != null && userService.findUserByMailAddress(userInfoRequest.getMailAddress()).getId() != userPrincipal.getId())
            return new ResponseEntity<>(new UpdateUserInfoResponse("Mail address already in use"), HttpStatus.CONFLICT);

        User user = userService.findUserById(userPrincipal.getId());
        User newUser = userService.updateUser(user, userInfoRequest);
        String newToken = jwtIssuerService.issue(
                userPrincipal.getId(),
                newUser.getUsername(),
                userPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList()
        );
        UpdateUserInfoResponse response = new UpdateUserInfoResponse(new UpdateUserInfoResponseType(newUser, newToken));
        return new ResponseEntity<>(response, HttpStatus.OK);
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
    public ResponseEntity<UserResponse> getUser(@PathVariable(name = "id") long id) {
        return new ResponseEntity<>(new UserResponse(userService.findUserById(id)), HttpStatus.OK);
    }
}
