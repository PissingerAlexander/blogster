import {Component, OnInit} from '@angular/core';
import {MatList, MatListItem} from "@angular/material/list";
import {Post} from "../../../model/post/post";
import {ActivatedRoute, Router} from "@angular/router";
import {PostService} from "../../../services/api/post.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {catchError, throwError} from "rxjs";
import {HttpErrorResponse} from "@angular/common/http";
import {handleErrorAndShowSnackBar} from "../../ErrorSnackBar/HandleErrorAndShowSnackBar";
import {DATE_FORMAT} from "../../../config/date-format";

@Component({
  selector: 'app-post-list',
  standalone: true,
    imports: [
        MatList,
        MatListItem
    ],
  templateUrl: './post-list.component.html',
  styleUrls: ['./post-list.component.scss', '../../../styles/list.scss']
})
export class PostListComponent implements OnInit {
  userId: number | undefined;
  blogId: number | undefined;
  postList: Post[] = [];

  dateFormat = DATE_FORMAT;

  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private postService: PostService,
    private snackBar: MatSnackBar
  ) {
  }

  updatePostList() {
    if (!this.userId) return;
    if (!this.blogId) return;
    this.postService.getAllPosts(this.blogId)
      .pipe(catchError((error: HttpErrorResponse) => {
        handleErrorAndShowSnackBar(error.error.error, this.snackBar);
        return throwError(() => new Error('Something bad happened; please try again later'));
      }))
      .subscribe((response: Post[]) => {
        response.map((value: Post) => value.createdAt = new Date(value.createdAt));
        this.postList = response;
      })
  }

  navigateToPost(event: any, postId: number) {
    if (event.target.classList[0] == 'listItemContent' || event.target.classList[0] == 'listItem') {
      this.router.navigate(['/', this.userId, 'blog', this.blogId, 'post', postId]).then();
    }
  }

  ngOnInit() {
    this.activatedRoute.params.subscribe((parameterList) => {
      this.userId = parameterList['userId'];
      this.blogId = parameterList['blogId'];
    })
    this.updatePostList();
  }
}
