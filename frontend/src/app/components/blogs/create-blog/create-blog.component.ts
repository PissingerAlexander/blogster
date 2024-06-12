import {Component, OnInit} from '@angular/core';
import {MatButton, MatIconButton} from "@angular/material/button";
import {BlogService} from "../../../services/api/blog.service";
import {CreateBlogRequest} from "../../../model/blog/http/create_blog/CreateBlogRequest";
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {catchError, throwError} from "rxjs";
import {
  MatDialog,
  MatDialogActions,
  MatDialogClose,
  MatDialogContent,
  MatDialogRef,
  MatDialogTitle
} from "@angular/material/dialog";
import {MatError, MatFormField, MatLabel, MatSuffix} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {ActivatedRoute, Router} from "@angular/router";
import {MatIcon} from "@angular/material/icon";
import {MatOption, MatSelect} from "@angular/material/select";
import {AuthService} from "../../../services/auth/auth.service";
import {handleErrorAndShowSnackBar} from "../../ErrorSnackBar/HandleErrorAndShowSnackBar";
import {HttpErrorResponse} from "@angular/common/http";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-create-blog',
  standalone: true,
  imports: [
    MatButton,
    FormsModule
  ],
  templateUrl: './create-blog.component.html',
  styleUrl: './create-blog.component.scss'
})
export class CreateBlogComponent implements OnInit {
  userId: number | undefined;
  currentUserId: number | undefined;

  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private authService: AuthService,
    public dialog: MatDialog
  ) {
  }

  openDialog() {
    const dialogRef = this.dialog.open(CreateBlogDialog);
    dialogRef.afterClosed().subscribe((result: number) => {
      if (result) this.router.navigate([this.userId, 'blog', result]).then();
    });
  }

  ngOnInit() {
    this.activatedRoute.params.subscribe(parameterList => {
      this.userId = parameterList['userId'];
    });
    this.currentUserId = this.authService.getId();
  }
}

@Component({
  selector: 'create-blog-dialog',
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
    MatOption
  ],
  templateUrl: './create-blog-dialog/create-blog.dialog.html',
  styleUrl: './create-blog-dialog/create-blog.dialog.scss'
})
export class CreateBlogDialog {
  createBlogForm = new FormGroup({
    blogName: new FormControl<string>('', [Validators.required, Validators.minLength(4), Validators.maxLength(128)])
  })
  blogNameErrorMessage = '';

  constructor(
    private blogService: BlogService,
    public dialogRef: MatDialogRef<CreateBlogDialog>,
    private snackBar: MatSnackBar
  ) {
  }

  updateBlogNameErrorMessage() {
    if (this.createBlogForm.controls.blogName.hasError('required')) {
      this.blogNameErrorMessage = 'You need to enter your blog name';
    } else if (this.createBlogForm.controls.blogName.hasError('minlength')) {
      this.blogNameErrorMessage = 'The blog name must have at least 3 characters';
    } else if (this.createBlogForm.controls.blogName.hasError('maxlength')) {
      this.blogNameErrorMessage = 'The blog name can\'t be longer than 64 characters';
    } else {
      this.blogNameErrorMessage = '';
    }
  }

  createBlog() {
    let createBlogRequest: CreateBlogRequest = {
      blogName: this.createBlogForm.controls.blogName.value!
    }
    this.blogService.createBlog(createBlogRequest)
      .pipe(catchError((error: HttpErrorResponse) => {
        handleErrorAndShowSnackBar(error.error.error, this.snackBar);
        return throwError(() => new Error('Something bad happened; please try again later'));
      }))
      .subscribe((res) => {
        this.dialogRef.close(res.data!.id);
        this.createBlogForm.reset();
      });
  }
}
