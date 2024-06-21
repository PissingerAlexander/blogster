import {Component, OnInit} from '@angular/core';
import {MatProgressSpinner} from "@angular/material/progress-spinner";
import {SpotifyAuthService} from "../../../services/auth/spotify-auth.service";
import {ActivatedRoute} from "@angular/router";
import {environment} from "../../../../environments/environment";

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    MatProgressSpinner
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent implements OnInit {

  constructor(
    private spotifyAuthService: SpotifyAuthService,
    private activatedRoute: ActivatedRoute
  ) {
  }

  ngOnInit() {
    if (this.activatedRoute.snapshot.queryParams['code'] !== undefined)
      this.spotifyAuthService.requestAccessTokenWithAuthorizationCode(this.activatedRoute.snapshot.queryParams['code'])
        .subscribe(res => {
          this.spotifyAuthService.setSpotifyTokens(res.data!.access_token, res.data!.refresh_token);
          document.location.href = environment.profilePath;
        });
    else document.location.href = environment.profilePath;
  }
}
