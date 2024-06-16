import {Component, OnInit} from '@angular/core';
import {MatFormField, MatHint, MatInput} from "@angular/material/input";
import {MatIconButton} from "@angular/material/button";
import {MatIcon} from "@angular/material/icon";
import {FormControl, ReactiveFormsModule} from "@angular/forms";
import {CommentService} from "../../../services/api/comment.service";
import {CreateCommentRequest} from "../../../model/comment/http/create_comment/CreateCommentRequest";
import {catchError, throwError} from "rxjs";
import {HttpErrorResponse} from "@angular/common/http";
import {handleErrorAndShowSnackBar} from "../../ErrorSnackBar/HandleErrorAndShowSnackBar";
import {ActivatedRoute} from "@angular/router";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-create-comment',
  standalone: true,
  imports: [
    MatInput,
    MatIconButton,
    MatIcon,
    MatFormField,
    ReactiveFormsModule,
    MatHint
  ],
  templateUrl: './create-comment.component.html',
  styleUrl: './create-comment.component.scss'
})
export class CreateCommentComponent implements OnInit {
  userId: number | undefined;
  blogId: number | undefined;
  postId: number | undefined;
  commentFormControl = new FormControl<string>('');

  constructor(
    private commentService: CommentService,
    private activatedRoute: ActivatedRoute,
    private snackBar: MatSnackBar) {
  }

  createComment() {
    let createCommentRequest: CreateCommentRequest = {
      postId: this.postId!,
      comment: this.commentFormControl.value!,
    }
    this.commentService.createComment(createCommentRequest)
      .pipe(catchError((error: HttpErrorResponse) => {
        handleErrorAndShowSnackBar(error.error.error, this.snackBar);
        return throwError(() => new Error('Something bad happened; please try again later'));
      }))
      .subscribe((res) => {
        this.commentFormControl.setValue('');
      });
  }


  ngOnInit() {
    this.activatedRoute.params.subscribe((parameterList) => {
      this.userId = parameterList['userId'];
      this.blogId = parameterList['blogId'];
      this.postId = parameterList['postId'];
    });
  }
}
