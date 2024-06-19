import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {Post} from "../../../model/post/post";
import {MatSnackBar} from "@angular/material/snack-bar";
import {AuthService} from "../../../services/auth/auth.service";
import {ActivatedRoute} from "@angular/router";
import {PostService} from "../../../services/api/post.service";
import {UpdatePostRequest} from "../../../model/post/http/update_post/UpdatePostRequest";
import {catchError, throwError} from "rxjs";
import {HttpErrorResponse} from "@angular/common/http";
import {handleErrorAndShowSnackBar} from "../../ErrorSnackBar/HandleErrorAndShowSnackBar";
import {UpdatePostResponse} from "../../../model/post/http/update_post/UpdatePostResponse";
import {PostForm} from "../PostForm";
import {PageTitleComponent} from "../../page-elements/page-title/page-title.component";
import {MatIcon} from "@angular/material/icon";
import {MatButton, MatIconButton} from "@angular/material/button";
import {MatError, MatFormField, MatInput, MatLabel} from "@angular/material/input";
import {ReactiveFormsModule} from "@angular/forms";
import {GetPostResponse} from "../../../model/post/http/get_post/GetPostResponse";
import {CommentService} from "../../../services/api/comment.service";
import {SpotifyService} from "../../../services/api/spotify.service";

enum Mode {
  EDIT = 'EDIT',
  VIEW = 'VIEW'
}

@Component({
  selector: 'app-post-details',
  standalone: true,
  imports: [
    PageTitleComponent,
    MatIcon,
    MatIconButton,
    MatInput,
    MatButton,
    ReactiveFormsModule,
    MatFormField,
    MatError,
    MatLabel
  ],
  templateUrl: './post-details.component.html',
  styleUrls: ['./post-details.component.scss', '../../../styles/page-title.scss']
})
export class PostDetailsComponent implements OnInit {
  @Output() modeChange = new EventEmitter<Mode>();
  mode: Mode = Mode.VIEW;

  userId: number | undefined;
  currentUserId: number | undefined;
  blogId: number | undefined;
  postId: number | undefined;
  post: Post | undefined;

  constructor(
    private authService: AuthService,
    private activatedRoute: ActivatedRoute,
    private postService: PostService,
    private snackBar: MatSnackBar,
    public updatePostForm: PostForm,
    private commentService: CommentService,
    private spotifyService: SpotifyService
  ) {
  }

  setModeToEdit() {
    this.mode = Mode.EDIT;
    this.modeChange.emit(this.mode);
  }

  setModeToView() {
    this.mode = Mode.VIEW;
    this.modeChange.emit(this.mode);
  }

  updatePost() {
    let updatePostRequest: UpdatePostRequest = {
      id: this.postId!,
      blogId: this.blogId!,
      postTitle: this.updatePostForm.postForm.controls.postTitle.value!,
      content: this.updatePostForm.postForm.controls.content.value!
    };
    this.postService.updatePost(updatePostRequest)
      .pipe(catchError((error: HttpErrorResponse) => {
        handleErrorAndShowSnackBar(error.error.error, this.snackBar);
        return throwError(() => new Error('Something bad happened; please try again later'));
      }))
      .subscribe((response: UpdatePostResponse) => {
        this.post!.postTitle = response.data!.postTitle!;
        this.post!.content = response.data!.content!;
        this.updatePostForm.postForm.controls.postTitle.setValue(this.post!.postTitle);
        this.updatePostForm.postForm.controls.content.setValue(this.post!.content);
        this.setModeToView();
      })
  }

  public addSongToFavourite() {
    this.spotifyService.addSongToFavourite(this.post!.trackId)
      .pipe(catchError((error: HttpErrorResponse) => {
        handleErrorAndShowSnackBar(error.error.error, this.snackBar);
        return throwError(() => new Error('Something bad happened; please try again later'));
      }))
      .subscribe();
  }

  ngOnInit() {
    this.activatedRoute.params.subscribe((parameterList => {
      this.userId = parameterList['userId'];
      this.blogId = parameterList['blogId'];
      this.postId = parameterList['postId'];
      this.postService.getPost(this.postId!)
        .pipe(catchError((error: HttpErrorResponse) => {
          handleErrorAndShowSnackBar(error.error.error, this.snackBar);
          return throwError(() => new Error('Something bad happened; please try again later'));
        }))
        .subscribe((res: GetPostResponse) => {
          this.post = res.data!;
          this.updatePostForm.postForm.controls.postTitle.setValue(this.post.postTitle);
          this.updatePostForm.postForm.controls.content.setValue(this.post.content);
        });
      this.commentService.getAllComments(this.postId!)
        .pipe(catchError((error: HttpErrorResponse) => {
          console.log(error);
          handleErrorAndShowSnackBar(error.error.error, this.snackBar);
          return throwError(() => new Error('Something bad happened; please try again later'));
        }))
        .subscribe(res => {
          this.commentService.setCommentList(res);
        });
    }));
    this.currentUserId = this.authService.getId();
  }
}
