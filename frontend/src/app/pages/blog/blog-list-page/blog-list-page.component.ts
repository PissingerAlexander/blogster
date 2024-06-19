import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, RouterLink} from "@angular/router";
import {HeaderComponent} from "../../../components/page-elements/header/header.component";
import {CreateBlogComponent} from "../../../components/blogs/create-blog/create-blog.component";
import {BlogListComponent} from "../../../components/blogs/blog-list/blog-list.component";
import {UserService} from "../../../services/api/user.service";
import {SpotifyAuthService} from "../../../services/auth/spotify-auth.service";

@Component({
  selector: 'app-blog-list-page',
  standalone: true,
  imports: [
    HeaderComponent,
    CreateBlogComponent,
    BlogListComponent
  ],
  templateUrl: './blog-list-page.component.html',
  styleUrl: './blog-list-page.component.scss'
})
export class BlogListPageComponent implements OnInit {
  id: number | null = null;

  constructor(
    private activeRoute: ActivatedRoute,
    private userService: UserService,
    private spotifyAuthService: SpotifyAuthService,
  ) {
  }

  ngOnInit() {
    this.userService.getCurrentUserInfo()
      .pipe()
      .subscribe((res) => {
        if (this.spotifyAuthService.getSpotifyAccessToken()) return;
        if (res.data!.spotifyAuthorized && this.spotifyAuthService.getSpotifyRefreshToken()) this.spotifyAuthService.requestAccessTokenWithRefreshToken();
      })
    this.activeRoute.params.subscribe((parameterList => {
      this.id = parameterList['userId'];
    }));

  }
}
