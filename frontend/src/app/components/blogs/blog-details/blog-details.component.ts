import {Component, Inject, OnInit} from '@angular/core';
import {BlogService} from "../../../services/api/blog.service";
import {Blog} from "../../../model/blog/blog";
import {ActivatedRoute, RouterLink} from "@angular/router";
import {catchError, throwError} from "rxjs";
import {GetBlogResponse} from "../../../model/blog/http/get_blog/GetBlogResponse";
import {MatIcon} from "@angular/material/icon";
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
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {MatError, MatFormField, MatLabel} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {AuthService} from "../../../services/auth/auth.service";
import {handleErrorAndShowSnackBar} from "../../ErrorSnackBar/HandleErrorAndShowSnackBar";
import {HttpErrorResponse} from "@angular/common/http";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-blog-details',
  standalone: true,
  imports: [
    MatIcon,
    MatIconButton,
    RouterLink
  ],
  templateUrl: './blog-details.component.html',
  styleUrl: './blog-details.component.scss'
})
export class BlogDetailsComponent implements OnInit {
  userId: number | null = null;
  currentUserId: number | null = null;
  blogId: number | null = null;
  blog: Blog | undefined;

  constructor(private authService: AuthService, private activeRoute: ActivatedRoute, private blogService: BlogService, public dialog: MatDialog, private snackBar: MatSnackBar) {
  }

  openDialog() {
    const dialogRef = this.dialog.open(BlogSettingsDialog, {
      data: {...this.blog}
    });
    dialogRef.afterClosed().subscribe(result => {
      if (result) this.blog = result;
    });
  }

  ngOnInit() {
    this.activeRoute.params.subscribe((parameterList => {
      this.userId = parameterList['userId'];
      this.blogId = parameterList['blogId'];
      this.blogService.getBlog(this.blogId!)
        .pipe(catchError((error: HttpErrorResponse) => {
        handleErrorAndShowSnackBar(error.error.error, this.snackBar);
        return throwError(() => new Error('Something bad happened; please try again later'));
      }))
        .subscribe((response: GetBlogResponse) => {
          this.blog = response.data!;
        });
    }));
    this.currentUserId = this.authService.getId();
  }
}

@Component({
  selector: 'blog-settings-dialog',
  standalone: true,
  imports: [
    MatDialogTitle,
    MatDialogContent,
    ReactiveFormsModule,
    MatFormField,
    MatInput,
    MatDialogActions,
    MatButton,
    MatLabel,
    MatError,
    MatDialogClose
  ],
  templateUrl: './blog-settings-dialog/blog-settings.dialog.html',
  styleUrl: './blog-settings-dialog/blog-settings.dialog.scss'
})
export class BlogSettingsDialog {
  blog: Blog;
  updateBlogForm = new FormGroup({
    blogName: new FormControl<string>('', [Validators.required, Validators.minLength(4), Validators.maxLength(128)]),
  });
  blogNameErrorMessage = '';


  constructor(
    @Inject(MAT_DIALOG_DATA) public data: Blog,
    private blogService: BlogService,
    private dialogRef: MatDialogRef<BlogSettingsDialog>,
    private snackBar: MatSnackBar
  ) {
    this.blog = data;
    this.updateBlogForm.controls.blogName.setValue(data.blogName);
  }

  updateBlogNameErrorMessage() {
    if (this.updateBlogForm.controls.blogName.hasError('required')) {
      this.blogNameErrorMessage = 'You need to enter your blog name';
    } else if (this.updateBlogForm.controls.blogName.hasError('minlength')) {
      this.blogNameErrorMessage = 'The blog name must have at least 3 characters';
    } else if (this.updateBlogForm.controls.blogName.hasError('maxlength')) {
      this.blogNameErrorMessage = 'The blog name can\'t be longer than 64 characters';
    } else {
      this.blogNameErrorMessage = '';
    }
  }

  updateBlog() {
    this.blog.blogName = this.updateBlogForm.controls.blogName.value!;
    this.blogService.updateBlog(this.blog)
      .pipe(catchError((error: HttpErrorResponse) => {
        handleErrorAndShowSnackBar(error.error.error, this.snackBar);
        return throwError(() => new Error('Something bad happened; please try again later'));
      }))
      .subscribe(res => {
        this.dialogRef.close(res.data!);
        this.updateBlogForm.reset();
      });

  }
}
