import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {HeaderComponent} from "../../../components/page-elements/header/header.component";
import {CreateBlogComponent} from "../../../components/blogs/create-blog/create-blog.component";
import {BlogListComponent} from "../../../components/blogs/blog-list/blog-list.component";
import {UserService} from "../../../services/api/user.service";
import {SpotifyAuthService} from "../../../services/auth/spotify-auth.service";
import {catchError, throwError} from "rxjs";
import {HttpErrorResponse} from "@angular/common/http";
import {handleErrorAndShowSnackBar} from "../../../components/ErrorSnackBar/HandleErrorAndShowSnackBar";
import {MatSnackBar} from "@angular/material/snack-bar";

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
    private snackBar: MatSnackBar
  ) {
  }

  ngOnInit() {
    this.userService.getCurrentUserInfo()
      .pipe(catchError((error: HttpErrorResponse) => {
        handleErrorAndShowSnackBar(error.error.error, this.snackBar);
        return throwError(() => new Error('Something bad happened; please try again later'));
      }))
      .subscribe();
    this.spotifyAuthService.getSpotifyAccessToken();
    this.activeRoute.params.subscribe((parameterList => {
      this.id = parameterList['userId'];
    }));
  }
}
