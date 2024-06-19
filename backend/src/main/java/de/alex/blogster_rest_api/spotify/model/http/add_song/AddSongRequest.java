package de.alex.blogster_rest_api.spotify.model.http.add_song;

import de.alex.blogster_rest_api.spotify.model.http.ResponseType.Track;
import jakarta.validation.constraints.NotNull;

public class AddSongRequest {
    @NotNull
    Track track;

    public @NotNull Track getTrack() {
        return track;
    }
}
