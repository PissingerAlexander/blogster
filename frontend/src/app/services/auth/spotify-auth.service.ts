import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {Observable, shareReplay} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class SpotifyAuthService {

  constructor(private http: HttpClient) { }

  public spotifyLogin(): Observable<any> {
    return this.http.get<any>(environment.apiUrl + "/spotify/login/")
      .pipe(shareReplay(1));
  }

  public requestAccessToken(code: string): Observable<any> {
    return this.http.post<any>(environment.apiUrl + "/spotify/access_token?code=" + code, "")
      .pipe(shareReplay(1));
  }
}
