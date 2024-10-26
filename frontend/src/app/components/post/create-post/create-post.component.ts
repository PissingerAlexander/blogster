import {Component, Inject, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {AuthService} from "../../../services/auth/auth.service";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {
  MAT_DIALOG_DATA,
  MatDialog,
  MatDialogActions,
  MatDialogClose,
  MatDialogContent,
  MatDialogRef,
  MatDialogTitle
} from "@angular/material/dialog";
import {MatButton, MatIconButton} from "@angular/material/button";
import {MatError, MatFormField, MatLabel, MatSuffix} from "@angular/material/form-field";
import {MatIcon} from "@angular/material/icon";
import {MatInput} from "@angular/material/input";
import {MatOption, MatSelect} from "@angular/material/select";
import {PostService} from "../../../services/api/post.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {CreatePostRequest} from "../../../model/post/http/create_post/CreatePostRequest";
import {catchError, throwError} from "rxjs";
import {HttpErrorResponse} from "@angular/common/http";
import {handleErrorAndShowSnackBar} from "../../ErrorSnackBar/HandleErrorAndShowSnackBar";
import {PostForm} from "../PostForm";
import {MatAutocomplete, MatAutocompleteTrigger} from "@angular/material/autocomplete";
import {SpotifyService} from "../../../services/api/spotify.service";
import {Track} from "../../../model/spotify/http/ResponseType/SongListResponseType";
import {SpotifyAuthService} from "../../../services/auth/spotify-auth.service";

@Component({
  selector: 'app-create-post',
  standalone: true,
  imports: [
    MatButton
  ],
  templateUrl: './create-post.component.html',
  styleUrl: './create-post.component.scss'
})
export class CreatePostComponent implements OnInit {
  userId: number | undefined;
  currentUserId: number | undefined;
  blogId: number | undefined;

  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private authService: AuthService,
    public dialog: MatDialog,
    private spotifyAuthService: SpotifyAuthService
  ) {
  }

  openDialog() {
    const dialogRef = this.dialog.open(CreatePostDialog, {
      data: {
        blogId: this.blogId
      }
    });
    dialogRef.afterClosed().subscribe((result: number) => {
      if (result) this.router.navigate([this.userId, 'blog', this.blogId, 'post', result]).then();
    });
  }

  ngOnInit() {
    this.activatedRoute.params.subscribe(parameterList => {
      this.userId = parameterList['userId'];
      this.blogId = parameterList['blogId'];
    });
    this.currentUserId = this.authService.getId();
  }
}

@Component({
  selector: 'create-post-dialog',
  standalone: true,
  imports: [
    MatDialogTitle,
    MatDialogContent,
    MatButton,
    MatDialogActions,
    MatDialogClose,
    FormsModule,
    MatError,
    MatFormField,
    MatIcon,
    MatIconButton,
    MatInput,
    MatSuffix,
    MatLabel,
    ReactiveFormsModule,
    MatSelect,
    MatOption,
    MatAutocomplete,
    MatAutocompleteTrigger
  ],
  templateUrl: './create-post-dialog/create-post.dialog.html',
  styleUrl: './create-post-dialog/create-post.dialog.scss'
})
export class CreatePostDialog {
  blogId: number
  songSuggestions: Track[] = [];
  track: Track | undefined;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: {blogId: number},
    private postService: PostService,
    public dialogRef: MatDialogRef<CreatePostDialog>,
    private snackBar: MatSnackBar,
    public createPostForm: PostForm,
    private spotifyService: SpotifyService
  ) {
    this.createPostForm.postForm.controls.postTitle.setValue('');
    this.createPostForm.postForm.controls.content.setValue('');
    this.blogId = data.blogId;
  }

  // FIXME: if not authorized spotify
  fetchSongList(value: string) {
    if (this.track) this.track.id = '';
    if (value == '') return;
    this.spotifyService.getSongList(value)
      .pipe(catchError((error: HttpErrorResponse) => {
        handleErrorAndShowSnackBar(error.error.error, this.snackBar);
        return throwError(() => new Error('Something bad happened; please try again later'));
      }))
      .subscribe((res) => {
        this.songSuggestions = res.tracks.items;
      })
  }

  getTrackList() {
    if (this.songSuggestions) return this.songSuggestions;
    else return [];
  }

  setTrack(track: Track) {
    this.track = track;
  }

  getTrackValid() {
    if (!this.track) return false;
    return this.track.id != '';
  }

  createPost() {
    let createPostRequest: CreatePostRequest = {
      blogId: this.blogId,
      postTitle: this.createPostForm.postForm.controls.postTitle.value!,
      content: this.createPostForm.postForm.controls.content.value!,
      trackId: this.track!.id,
      trackName: this.track!.name
    };
    this.postService.createPost(createPostRequest)
      .pipe(catchError((error: HttpErrorResponse) => {
        handleErrorAndShowSnackBar(error.error.error, this.snackBar);
        return throwError(() => new Error('Something bad happened; please try again later'));
      }))
      .subscribe((res) => {
        this.createPostForm.postForm.reset();
        this.dialogRef.close(res.data!.id);
      });
  }
}
