import {Component, OnInit} from '@angular/core';
import {MatList} from "@angular/material/list";
import {CommentService} from "../../../services/api/comment.service";
import {ActivatedRoute, Router, RouterLink} from "@angular/router";
import {AuthService} from "../../../services/auth/auth.service";
import {Comment} from "../../../model/comment/comment"
import {catchError, throwError} from "rxjs";
import {HttpErrorResponse} from "@angular/common/http";
import {handleErrorAndShowSnackBar} from "../../ErrorSnackBar/HandleErrorAndShowSnackBar";
import {MatSnackBar} from "@angular/material/snack-bar";
import {MatIconButton} from "@angular/material/button";
import {MatIcon} from "@angular/material/icon";

@Component({
  selector: 'app-comment-list',
  standalone: true,
  imports: [
    MatList,
    MatIconButton,
    MatIcon,
    RouterLink
  ],
  templateUrl: './comment-list.component.html',
  styleUrl: './comment-list.component.scss'
})
export class CommentListComponent implements OnInit {
  currentUserId: number | undefined;
  userId: number | undefined;
  blogId: number | undefined;
  postId: number | undefined;

  commentList: Comment[] = [];

  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private commentService: CommentService,
    private authService: AuthService,
    private snackBar: MatSnackBar
  ) {
    this.commentService.getCommentListObservable()
      .subscribe((res) => {
        this.commentList = res;
      })
  }

  ngOnInit() {
    this.activatedRoute.params.subscribe((parameterList) => {
      this.userId = parameterList['userId'];
      this.blogId = parameterList['blogId'];
      this.postId = parameterList['postId'];
    });
    this.currentUserId = this.authService.getId();
    this.commentService.getAllComments(this.postId!)
      .pipe(catchError((error: HttpErrorResponse) => {
        console.log(error);
        handleErrorAndShowSnackBar(error.error.error, this.snackBar);
        return throwError(() => new Error('Something bad happened; please try again later'));
      }))
      .subscribe(res => {
        this.commentList = res;
      });
  }

  deleteComment(comment: Comment) {
    this.commentService.deleteComment(comment.id)
      .pipe(catchError((error: HttpErrorResponse) => {
        console.log(error);
        handleErrorAndShowSnackBar(error.error.error, this.snackBar);
        return throwError(() => new Error('Something bad happened; please try again later'));
      }))
      .subscribe();
    this.commentService.removeCommentFromList(comment);
  }
}
