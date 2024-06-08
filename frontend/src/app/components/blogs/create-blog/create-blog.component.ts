import {Component} from '@angular/core';
import {MatButton} from "@angular/material/button";
import {BlogService} from "../../../services/api/blog.service";
import {CreateBlogRequest} from "../../../model/blog/http/create_blog/CreateBlogRequest";
import {FormsModule} from "@angular/forms";
import {catchError, throwError} from "rxjs";
import {HttpErrorResponse} from "@angular/common/http";
import {CreateBlogResponse} from "../../../model/blog/http/create_blog/CreateBlogResponse";

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
export class CreateBlogComponent {
  public userId: number | undefined;
  public blogName: string = "";

  constructor(private blogService: BlogService) {
  }

  public createBlog() {
    console.log(this.blogName);
    if (this.blogName == "") return;
    let createBlogRequest: CreateBlogRequest = {
      blogName: this.blogName
    }
    this.blogService.createBlog(createBlogRequest)
      .pipe(catchError((error: HttpErrorResponse) => {
        console.error(error);
        return throwError(() => new Error('Something bad happened; please try again'));
      }))
      .subscribe((blog: CreateBlogResponse) => {
        console.log(blog);
      })
  }
}
