package de.alex.blogster_rest_api.spotify.controller;

import de.alex.blogster_rest_api.spotify.config.SpotifyConfiguration;
import de.alex.blogster_rest_api.spotify.service.SpotifyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/spotify/")
public class SpotifyController {
    private final SpotifyConfiguration spotifyConfiguration;
    private final SpotifyService spotifyService;

    public SpotifyController(SpotifyConfiguration spotifyConfiguration, SpotifyService spotifyService) {
        this.spotifyConfiguration = spotifyConfiguration;
        this.spotifyService = spotifyService;
    }

    @GetMapping(path = "/login/")
    public ResponseEntity<String> loginToSpotify() {
        String requestUrl = "{\"data\":\"https://accounts.spotify.com/authorize" +
                "?response_type=code" +
                "&client_id=" + spotifyConfiguration.getClientId() +
                "&scope=" + spotifyConfiguration.getScope() +
                "&redirect_uri=" + spotifyConfiguration.getRedirectUri() +
                "\"}";
        return new ResponseEntity<>(requestUrl, HttpStatus.OK);
    }

    @PostMapping(path = "/access_token")
    public ResponseEntity<String> requestAccessToken(@RequestParam(name = "code") String code) {
        String accessToken = spotifyService.requestSpotifyAccessToken(code);
        return new ResponseEntity<>(accessToken, HttpStatus.OK);
    }
}
