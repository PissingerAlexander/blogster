import {Component, OnInit} from '@angular/core';
import {MatProgressSpinner} from "@angular/material/progress-spinner";
import {SpotifyAuthService} from "../../../services/auth/spotify-auth.service";
import {ActivatedRoute} from "@angular/router";
import {AuthService} from "../../../services/auth/auth.service";

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
    private authService: AuthService,
    private spotifyAuthService: SpotifyAuthService,
    private activatedRoute: ActivatedRoute
  ) {
  }

  ngOnInit() {
    if (this.activatedRoute.snapshot.queryParams['code'] !== undefined)
      this.spotifyAuthService.requestAccessToken(this.activatedRoute.snapshot.queryParams['code'])
        .subscribe(res => {
          this.authService.setSpotifyToken(res['access_token'])
          console.log(this.authService.getSpotifyToken());
          document.location.href = 'http://localhost:4200/profile';
        });
  }
}
