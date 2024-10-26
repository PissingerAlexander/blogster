package de.alex.blogster_rest_api.spotify.controller;

import de.alex.blogster_rest_api.security.authentication.UserPrincipal;
import de.alex.blogster_rest_api.spotify.config.SpotifyConfiguration;
import de.alex.blogster_rest_api.spotify.model.http.ResponseType.GetAccessTokenResponseType;
import de.alex.blogster_rest_api.spotify.model.http.get_access_token.GetAccessTokenResponse;
import de.alex.blogster_rest_api.spotify.service.SpotifyService;
import de.alex.blogster_rest_api.user.model.User;
import de.alex.blogster_rest_api.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/spotify/")
public class SpotifyController {
    private final SpotifyConfiguration spotifyConfiguration;
    private final SpotifyService spotifyService;
    private final UserService userService;

    public SpotifyController(SpotifyConfiguration spotifyConfiguration, SpotifyService spotifyService, UserService userService) {
        this.spotifyConfiguration = spotifyConfiguration;
        this.spotifyService = spotifyService;
        this.userService = userService;
    }

    @GetMapping(path = "/authorize/")
    public ResponseEntity<String> authorizeSpotifyOrLogin() {
        String requestUrl = "{\"redirectUrl\":\"https://accounts.spotify.com/authorize" +
                "?response_type=code" +
                "&client_id=" + spotifyConfiguration.getClientId() +
                "&scope=" + spotifyConfiguration.getScope() +
                "&redirect_uri=" + spotifyConfiguration.getRedirectUri() +
                "\"}";
        return new ResponseEntity<>(requestUrl, HttpStatus.OK);
    }

    @PostMapping(path = "/access_token")
    public ResponseEntity<GetAccessTokenResponse> requestAccessToken(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestParam(required = false) String code, @RequestParam(required = false) String refreshToken) {
        GetAccessTokenResponseType accessTokenResponse;
        if (code != null) {
            accessTokenResponse = spotifyService.requestSpotifyAccessTokenWithAuthorizationCode(code);
            if (accessTokenResponse == null)
                return new ResponseEntity<>(new GetAccessTokenResponse("Authorization code invalid or expired"), HttpStatus.CONFLICT);
        } else if (refreshToken != null) {
            accessTokenResponse = spotifyService.requestSpotifyAccessTokenWithRefreshToken(refreshToken);
            if (accessTokenResponse == null)
                return new ResponseEntity<>(new GetAccessTokenResponse("Refresh token invalid or expired"), HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>(new GetAccessTokenResponse("No authorization code or refresh token given"), HttpStatus.CONFLICT);
        }
        User user = userService.findUserById(userPrincipal.getId());
        userService.setSpotifyAuthorization(user, true);
        return new ResponseEntity<>(new GetAccessTokenResponse(accessTokenResponse), HttpStatus.OK);

    }
}
