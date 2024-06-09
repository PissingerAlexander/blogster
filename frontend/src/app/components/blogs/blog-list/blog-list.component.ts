import {Component, Input, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {BlogService} from "../../../services/api/blog.service";
import {catchError, Subject, throwError} from "rxjs";
import {HttpErrorResponse} from "@angular/common/http";
import {Blog} from "../../../model/blog/blog";

@Component({
  selector: 'app-blog-list',
  standalone: true,
  imports: [],
  templateUrl: './blog-list.component.html',
  styleUrl: './blog-list.component.scss'
})
export class BlogListComponent implements OnInit {
  userId: number | undefined;
  blogList: Blog[] = [];

  constructor(private route: ActivatedRoute, private blogService: BlogService) {
  }

  updateBlogList() {
    if (!this.userId) return;
    this.blogService.getAllBlogs(this.userId)
      .pipe(catchError((error: HttpErrorResponse) => {
        console.error(error.error);
        // TODO: display info about error to user directly (on the form?)
        return throwError(() => new Error('Something bad happened; please try again'));
      }))
      .subscribe((response: Blog[]) => {
        this.blogList = response;
      })
  }

  ngOnInit() {
    this.route.params.subscribe((parameterList) => {
      this.userId = parameterList['userId'];
    })
    this.updateBlogList();
  }
}
