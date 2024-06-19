package de.alex.blogster_rest_api.spotify.model.http.add_song;

import de.alex.blogster_rest_api.http.model.response.CustomResponse;
import de.alex.blogster_rest_api.spotify.model.http.ResponseType.Track;

public class AddSongResponse extends CustomResponse<Track> {
    public AddSongResponse(Track data) {
        super(data);
    }

    public AddSongResponse(String error) {
        super(error);
    }
}
