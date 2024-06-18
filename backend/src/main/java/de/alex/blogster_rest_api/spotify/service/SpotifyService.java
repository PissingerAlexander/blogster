package de.alex.blogster_rest_api.spotify.service;

import de.alex.blogster_rest_api.spotify.config.SpotifyConfiguration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Base64;

@Service
public class SpotifyService {
    private final SpotifyConfiguration spotifyConfiguration;

    public SpotifyService(SpotifyConfiguration spotifyConfiguration) {
        this.spotifyConfiguration = spotifyConfiguration;
    }

    public void addOrRemoveTracksToFavourites(boolean add, ArrayList<String> tracks, String spotifyAccessToken) {
        RestTemplate restTemplate = new RestTemplate();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("https://api.spotify.com/v1/me/tracks?ids=");
        tracks.forEach(track -> stringBuilder.append(track).append(','));
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        String url = stringBuilder.toString();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + spotifyAccessToken);
        headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        if (add) {
            restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);
        } else {
            restTemplate.exchange(url, HttpMethod.DELETE, entity, String.class);
        }
    }

    public String requestSpotifyAccessToken(String code) {
        RestTemplate restTemplate = new RestTemplate();

        String body = "grant_type=authorization_code" +
                "&code=" + code +
                "&redirect_uri=" + spotifyConfiguration.getRedirectUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        String authHeader = spotifyConfiguration.getClientId() + ":" + spotifyConfiguration.getClientSecret();
        String encodedString = Base64.getEncoder().encodeToString(authHeader.getBytes());
        headers.setBasicAuth(encodedString);

        HttpEntity<?> entity = new HttpEntity<>(body, headers);

        HttpEntity<String> response = restTemplate.exchange(
                "https://accounts.spotify.com/api/token",
                HttpMethod.POST,
                entity,
                String.class
        );
        return response.getBody();
    }
}
