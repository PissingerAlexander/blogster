import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {GetSongListResponse} from "../../model/spotify/http/get_song_list/GetSongListResponse";
import {shareReplay} from "rxjs";
import {environment} from "../../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class SpotifyService {

  constructor(
    private http: HttpClient
  ) { }

  public getSongList(query: string) {
    return this.http.get<GetSongListResponse>(environment.spotifyApiUrl + `/search?q=${query}&type=track`)
      .pipe(shareReplay(1));
  }
}
