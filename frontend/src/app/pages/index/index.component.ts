import { Component } from '@angular/core';
import {HeaderComponent} from "../../components/page-elements/header/header.component";
import {PostService} from "../../services/api/post.service";
import {Post} from "../../model/post/post";
import {MatSnackBar} from "@angular/material/snack-bar";
import {catchError, throwError} from "rxjs";
import {HttpErrorResponse} from "@angular/common/http";
import {handleErrorAndShowSnackBar} from "../../components/ErrorSnackBar/HandleErrorAndShowSnackBar";
import {DATE_FORMAT} from "../../config/date-format";
import {MatIcon} from "@angular/material/icon";
import {MatIconButton} from "@angular/material/button";
import {MatList, MatListItem} from "@angular/material/list";
import {Router, RouterLink} from "@angular/router";

@Component({
  selector: 'app-index',
  standalone: true,
  imports: [
    HeaderComponent,
    MatIcon,
    MatIconButton,
    MatList,
    MatListItem,
    RouterLink
  ],
  templateUrl: './index.component.html',
  styleUrl: './index.component.scss'
})
export class IndexComponent {
  protected readonly dateFormat = DATE_FORMAT;
  postList: Post[] | undefined;

  constructor(
    private postService: PostService,
    private router: Router,
    private snackBar: MatSnackBar
  ) {
    this.getPostList();
  }

  public getPostList() {
    this.postService.getAllPosts()
      .pipe(catchError((error: HttpErrorResponse) => {
        console.log(error);
        handleErrorAndShowSnackBar(error.error.error, this.snackBar);
        return throwError(() => new Error('Something bad happened; please try again later'));
      }))
      .subscribe(res => {
        this.postList = res;
        console.log(this.postList)
      });
  }

  navigateToPost(post: Post) {
    this.router.navigate(['/', post.blog.owner.id, 'blog', post.blog.id, 'post', post.id]).then();
  }

}
